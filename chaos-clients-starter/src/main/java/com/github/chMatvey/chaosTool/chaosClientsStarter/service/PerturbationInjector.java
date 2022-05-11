package com.github.chMatvey.chaosTool.chaosClientsStarter.service;

import com.github.chMatvey.chaosTool.chaosModels.ChaosResponse;

public interface PerturbationInjector {
    boolean canInjectError(ChaosResponse chaosResponse);

    void injectError(ChaosResponse chaosResponse);
}
