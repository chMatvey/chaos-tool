package com.github.chMatvey.chaosTool.chaosModels;

public record ChaosResponse(
        Long generatedId,
        Integer order,
        Boolean injectError,
        Integer errorCode
) {
}
