package com.github.chMatvey.chaosTool.chaosServerStarter.service;

import com.github.chMatvey.chaosTool.chaosModels.*;

import java.util.Optional;

public interface ChaosService {
    ChaosResponse create(ChaosCreateRequest createRequest);

    ChaosResponse update(ChaosUpdateRequest updateRequest);

    Optional<ChaosSessionInfoResponse> get(Integer id);

    Optional<WasFaultInjectedResponse> wasFaultInjected(Integer chaosSessionId);
}
