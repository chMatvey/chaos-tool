package com.github.chMatvey.chaosTool.chaosClientsStarter.service.interceptor;

import com.github.chMatvey.chaosTool.chaosClientsStarter.ChaosClientException;
import com.github.chMatvey.chaosTool.chaosClientsStarter.service.ChaosClient;
import com.github.chMatvey.chaosTool.chaosClientsStarter.service.PerturbationInjector;
import com.github.chMatvey.chaosTool.chaosModels.ChaosResponse;
import com.github.chMatvey.chaosTool.chaosModels.ChaosUpdateRequest;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.github.chMatvey.chaosTool.chaosModels.ChaosHeaders.*;
import static com.github.chMatvey.chaosTool.chaosModels.ServiceRole.SENDER;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChaosFeignInterceptor implements RequestInterceptor {
    private final ChaosClient chaosClient;
    private final PerturbationInjector perturbationInjector;

    @Value("${spring.application.name}")
    private String currentServiceName;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("Intercepted Feign HTTP request");
        HttpServletRequest servletRequest = getCurrentRequest();
        Integer chaosSessionId = getHeaderFromRequestAttributeAsInteger(servletRequest, CHAOS_SESSION_ID_HEADER);
        Integer chaosTestCaseId = getHeaderFromRequestAttributeAsInteger(servletRequest, CHAOS_TEST_CASE_ID_HEADER);
        Integer chaosTestCaseStep = getHeaderFromRequestAttributeAsInteger(servletRequest, CHAOS_TEST_CASE_STEP_HEADER);
        ChaosUpdateRequest chaosUpdateRequest = ChaosUpdateRequest.builder()
                .sessionId(chaosSessionId)
                .testCaseId(chaosTestCaseId)
                .testCaseStep(chaosTestCaseStep)
                .sourceServiceName(currentServiceName)
                .targetServiceName(requestTemplate.feignTarget().name())
                .serviceRole(SENDER)
                .uri(requestTemplate.url())
                .method(requestTemplate.method())
                .build();
        ChaosResponse chaosResponse = chaosClient.update(chaosUpdateRequest);

        if (perturbationInjector.canInjectError(chaosResponse)) {
            perturbationInjector.injectError(chaosResponse);
        }
        requestTemplate.header(CHAOS_SESSION_ID_HEADER, chaosResponse.getSessionId().toString());
        requestTemplate.header(CHAOS_TEST_CASE_ID_HEADER, chaosResponse.getTestCaseId().toString());
        requestTemplate.header(CHAOS_TEST_CASE_STEP_HEADER, chaosResponse.getTestCaseStep().toString());
        requestTemplate.header(CHAOS_SENDER_SERVICE_HEADER, currentServiceName);
    }

    private HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes)requestAttributes).getRequest();
        }
        throw new ChaosClientException("Cannot get access to current HTTP servlet request");
    }

    private Integer getHeaderFromRequestAttributeAsInteger(HttpServletRequest request, String headerName) {
        Object attributeValue = ofNullable(request.getAttribute(headerName))
                .orElseThrow(() -> new ChaosClientException(format("%s request attribute does not exist", headerName)));
        try {
            return parseInt(attributeValue.toString());
        } catch (NumberFormatException e) {
            throw new ChaosClientException(format("Cannot parse %s request attribute value", headerName));
        }
    }
}
