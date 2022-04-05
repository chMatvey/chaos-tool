package com.github.chMatvey.chaosTool.chaosServerStarter.service;

import com.github.chMatvey.chaosTool.chaosModels.CreateRequest;
import com.github.chMatvey.chaosTool.chaosModels.ChaosResponse;
import com.github.chMatvey.chaosTool.chaosModels.UpdateRequest;

public interface ChaosService {
    ChaosResponse create(CreateRequest createRequest);

    ChaosResponse update(UpdateRequest updateRequest);
}
