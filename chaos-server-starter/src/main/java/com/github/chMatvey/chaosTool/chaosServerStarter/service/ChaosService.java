package com.github.chMatvey.chaosTool.chaosServerStarter.service;

import com.github.chMatvey.chaosTool.chaosModels.*;

import java.util.Optional;

public interface ChaosService {
    ChaosResponse createChaosSession(ChaosCreateRequest createRequest);

    ChaosResponse updateChaosSession(ChaosUpdateRequest updateRequest);

    Optional<ChaosSessionInfoResponse> getChaosSessionInfo(Integer id);

    Optional<WasFaultInjectedResponse> wasFaultInjected(Integer chaosSessionId);
}
