package com.github.chMatvey.chaosTool.chaosModels;

public interface ChaosRequest {
    String getSourceServiceName();
    String getTargetServiceName();
    String getUri();
    String getMethod();
}
