package com.github.chMatvey.chaosTool.chaosServerStarter.service;

import com.github.chMatvey.chaosTool.chaosModels.CreateRequest;
import com.github.chMatvey.chaosTool.chaosModels.CreateResponse;

public interface ChaosService {
    CreateResponse create(CreateRequest createRequest);
}
