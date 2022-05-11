package com.github.chMatvey.chaosTool.chaosServerStarter.util;

import com.github.chMatvey.chaosTool.chaosModels.ChaosCreateRequest;
import com.github.chMatvey.chaosTool.chaosModels.ChaosUpdateRequest;
import com.github.chMatvey.chaosTool.chaosServerStarter.model.RemoteCallStep;

import static com.github.chMatvey.chaosTool.chaosServerStarter.util.ErrorCodeUtil.DEFAULT_ERROR_CODES;

public class RemoteCallStepUtil {
    public static RemoteCallStep fromCreateRequest(ChaosCreateRequest createRequest) {
        return new RemoteCallStep(
                createRequest.getSourceServiceName(),
                createRequest.getTargetServiceName(),
                createRequest.getUri(),
                createRequest.getMethod(),
                DEFAULT_ERROR_CODES
        );
    }

    public static RemoteCallStep fromUpdateRequest(ChaosUpdateRequest updateRequest) {
        return new RemoteCallStep(
                updateRequest.getSourceServiceName(),
                updateRequest.getTargetServiceName(),
                updateRequest.getUri(),
                updateRequest.getMethod(),
                DEFAULT_ERROR_CODES
        );
    }
}
