package com.github.chMatvey.chaosTool.chaosServerStarter.model;

import com.github.chMatvey.chaosTool.chaosModels.ChaosRequest;

import java.util.Queue;

public record RemoteCallStep(
        String sourceServiceName,
        String targetServiceName,
        String uri,
        String method,
        Queue<Integer> injectedErrorCodes
) {
    public boolean equalCurrentRequest(ChaosRequest chaosRequest) {
        return equalsIgnoreCase(sourceServiceName, chaosRequest.getSourceServiceName()) &&
                equalsIgnoreCase(targetServiceName, chaosRequest.getTargetServiceName()) &&
                equalsIgnoreCase(uri, chaosRequest.getUri()) &&
                equalsIgnoreCase(method, chaosRequest.getMethod());
    }

    private static boolean equalsIgnoreCase(String s1, String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }
}
