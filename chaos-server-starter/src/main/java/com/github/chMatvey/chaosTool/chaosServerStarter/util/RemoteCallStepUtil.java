package com.github.chMatvey.chaosTool.chaosServerStarter.util;

import com.github.chMatvey.chaosTool.chaosModels.ChaosCreateRequest;
import com.github.chMatvey.chaosTool.chaosModels.ChaosUpdateRequest;
import com.github.chMatvey.chaosTool.chaosServerStarter.model.RemoteCallStep;

public class RemoteCallStepUtil {
    public static RemoteCallStep fromCreateRequest(ChaosCreateRequest createRequest) {
        return new RemoteCallStep(
                createRequest.getSourceServiceName(),
                createRequest.getTargetServiceName(),
                createRequest.getServiceRole(),
                createRequest.getUri(),
                createRequest.getMethod()
        );
    }

    public static RemoteCallStep fromUpdateRequest(ChaosUpdateRequest updateRequest) {
        return new RemoteCallStep(
                updateRequest.getSourceServiceName(),
                updateRequest.getTargetServiceName(),
                updateRequest.getServiceRole(),
                updateRequest.getUri(),
                updateRequest.getMethod()
        );
    }
}
