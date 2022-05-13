package com.github.chMatvey.chaosTool.chaosClientsStarter.service.interceptor;

import com.github.chMatvey.chaosTool.chaosClientsStarter.ChaosClientException;
import com.github.chMatvey.chaosTool.chaosClientsStarter.service.ChaosClient;
import com.github.chMatvey.chaosTool.chaosClientsStarter.service.PerturbationInjector;
import com.github.chMatvey.chaosTool.chaosModels.ChaosCreateRequest;
import com.github.chMatvey.chaosTool.chaosModels.ChaosResponse;
import com.github.chMatvey.chaosTool.chaosModels.ChaosUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static com.github.chMatvey.chaosTool.chaosModels.ChaosHeaders.*;
import static com.github.chMatvey.chaosTool.chaosModels.ServiceRole.RECIPIENT;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChaosServletInterceptor implements HandlerInterceptor {
    private final ChaosClient chaosClient;
    private final PerturbationInjector perturbationInjector;

    @Value("${spring.application.name}")
    private String currentServiceName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getRequestURI().contains("error")) {
            return false;
        }

        log.info("Intercepted HTTP servlet request");
        ArrayList<String> headersNames = Collections.list(request.getHeaderNames());

        boolean hasTestCaseIdHeader = headersNames.stream().anyMatch(headerName -> headerName.equals(CHAOS_TEST_CASE_ID_HEADER));
        ChaosResponse chaosResponse = hasTestCaseIdHeader ? sendChaosUpdateRequest(request) : sendChaosCreateRequest(request);

        if (perturbationInjector.canInjectError(chaosResponse)) {
            response.setStatus(chaosResponse.getErrorCode());
            return false;
        }
        request.setAttribute(CHAOS_SESSION_ID_HEADER, chaosResponse.getSessionId());
        request.setAttribute(CHAOS_TEST_CASE_ID_HEADER, chaosResponse.getTestCaseId());
        request.setAttribute(CHAOS_TEST_CASE_STEP_HEADER, chaosResponse.getTestCaseStep());

        response.setHeader(CHAOS_SESSION_ID_HEADER, chaosResponse.getSessionId().toString());
        response.setHeader(CHAOS_TEST_CASE_ID_HEADER, chaosResponse.getTestCaseId().toString());

        return true;
    }

    private ChaosResponse sendChaosCreateRequest(HttpServletRequest request) {
        log.info("Sending create test case request...");
        ChaosCreateRequest chaosCreateRequest = ChaosCreateRequest.builder()
                .sessionId(extractSessionIdHeaderValueOrReturnNull(request))
                .sourceServiceName(null)
                .targetServiceName(currentServiceName)
                .serviceRole(RECIPIENT)
                .uri(request.getRequestURI())
                .method(request.getMethod())
                .build();
        return chaosClient.create(chaosCreateRequest);
    }

    private ChaosResponse sendChaosUpdateRequest(HttpServletRequest request) {
        log.info("Sending update test case request...");
        ChaosUpdateRequest chaosUpdateRequest = ChaosUpdateRequest.builder()
                .sessionId(extractHttpHeaderValueAsIntegerOrThrowError(request, CHAOS_SESSION_ID_HEADER))
                .testCaseId(extractHttpHeaderValueAsIntegerOrThrowError(request, CHAOS_TEST_CASE_ID_HEADER))
                .testCaseStep(extractHttpHeaderValueAsIntegerOrThrowError(request, CHAOS_TEST_CASE_STEP_HEADER))
                .sourceServiceName(extractHttpHeaderValueAsStringOrThrowError(request, CHAOS_SENDER_SERVICE_HEADER))
                .targetServiceName(currentServiceName)
                .serviceRole(RECIPIENT)
                .uri(request.getRequestURI())
                .method(request.getMethod())
                .build();
        return chaosClient.update(chaosUpdateRequest);
    }

    private Integer extractSessionIdHeaderValueOrReturnNull(HttpServletRequest request) {
        String headerValue = request.getHeader(CHAOS_SESSION_ID_HEADER);
        if (headerValue == null) {
            return null;
        }
        try {
            return parseInt(headerValue);
        } catch (NumberFormatException e) {
            throw new ChaosClientException("Cannot parse Chaos Order header value", e);
        }
    }

    private String extractHttpHeaderValueAsStringOrThrowError(HttpServletRequest request, String headerName) {
        return Optional.ofNullable(request.getHeader(headerName))
                .orElseThrow(() -> new ChaosClientException(format("%s request header does not exist", headerName)));
    }

    private Integer extractHttpHeaderValueAsIntegerOrThrowError(HttpServletRequest request, String headerName) {
        String headerValue = extractHttpHeaderValueAsStringOrThrowError(request, headerName);
        try {
            return parseInt(headerValue);
        } catch (NumberFormatException e) {
            throw new ChaosClientException(format("Cannot parse %s request header value", headerName));
        }
    }
}
