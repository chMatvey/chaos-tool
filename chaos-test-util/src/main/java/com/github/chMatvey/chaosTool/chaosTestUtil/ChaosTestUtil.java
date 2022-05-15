package com.github.chMatvey.chaosTool.chaosTestUtil;

import com.github.chMatvey.chaosTool.chaosModels.ChaosSessionInfoResponse;
import com.github.chMatvey.chaosTool.chaosModels.WasFaultInjectedResponse;
import com.github.chMatvey.chaosTool.chaosTestUtil.model.InjectedFaultInfo;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.function.Consumer;

import static com.github.chMatvey.chaosTool.chaosModels.ChaosHeaders.CHAOS_SESSION_ID_HEADER;
import static com.github.chMatvey.chaosTool.chaosTestUtil.util.ChaosHeadersUtil.getSessionIdFromResponseHeaders;
import static com.github.chMatvey.chaosTool.chaosTestUtil.util.ChaosServerUtil.*;

public class ChaosTestUtil {
    private static final HttpClient httpClient = HttpClientBuilder.create().build();

    private static boolean wasFaultInjected;
    private static Integer chaosSessionId;

    @SneakyThrows
    public static void chaosTest(HttpUriRequest request, Consumer<HttpResponse> handler) {
        HttpResponse response = httpClient.execute(request);
        chaosSessionId = getSessionIdFromResponseHeaders(response);
        wasFaultInjected = false;

        handler.accept(response);

        HttpGet sessionInfoRequest = createGetChaosSessionInfoRequest(chaosSessionId);
        HttpResponse sessionInfoResponse = httpClient.execute(sessionInfoRequest);
        ChaosSessionInfoResponse sessionInfo = parseChaosSessionInfoResponse(sessionInfoResponse);
        Integer testCaseCount = sessionInfo.getTestCaseCount();
        request.setHeader(CHAOS_SESSION_ID_HEADER, chaosSessionId.toString());
        wasFaultInjected = true;

        // First test case - no error injection already completed
        for (int testCaseNumber = 1; testCaseNumber < testCaseCount; testCaseNumber++) {
            response = httpClient.execute(request);
            handler.accept(response);
        }
    }

    public static boolean wasFaultInjected() {
        return wasFaultInjected;
    }

    @SneakyThrows
    public static InjectedFaultInfo injectedFaultInfo() {
        HttpGet wasFaultInjectedRequest = createWasFaultInjectedRequest(chaosSessionId);
        HttpResponse wasFaultInjectedResponse = httpClient.execute(wasFaultInjectedRequest);
        WasFaultInjectedResponse wasFaultInjected = parseWasFaultInjectedResponse(wasFaultInjectedResponse);
        return InjectedFaultInfo.builder()
                .serviceName(wasFaultInjected.getServiceName())
                .errorCode(wasFaultInjected.getErrorCodeInjected())
                .build();
    }
}
