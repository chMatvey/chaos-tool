package com.github.chMatvey.chaosTool.chaosServerStarter.service;

import com.github.chMatvey.chaosTool.chaosModels.ChaosCreateRequest;
import com.github.chMatvey.chaosTool.chaosModels.ChaosResponse;
import com.github.chMatvey.chaosTool.chaosModels.ChaosUpdateRequest;

public interface ChaosService {
    ChaosResponse create(ChaosCreateRequest createRequest);

    ChaosResponse update(ChaosUpdateRequest updateRequest);
}
