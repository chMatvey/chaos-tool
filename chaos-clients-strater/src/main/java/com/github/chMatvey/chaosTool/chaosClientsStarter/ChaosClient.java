package com.github.chMatvey.chaosTool.chaosClientsStarter;

import com.github.chMatvey.chaosTool.chaosModels.ChaosResponse;
import com.github.chMatvey.chaosTool.chaosModels.CreateRequest;
import com.github.chMatvey.chaosTool.chaosModels.UpdateRequest;

public interface ChaosClient {
    ChaosResponse create(CreateRequest createRequest);

    ChaosResponse update(UpdateRequest updateRequest);
}
