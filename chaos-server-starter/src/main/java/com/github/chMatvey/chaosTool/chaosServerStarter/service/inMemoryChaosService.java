package com.github.chMatvey.chaosTool.chaosServerStarter.service;

import com.github.chMatvey.chaosTool.chaosModels.CreateRequest;
import com.github.chMatvey.chaosTool.chaosModels.CreateResponse;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class inMemoryChaosService implements ChaosService {
    private final Map<Long, CreateResponse> map = new ConcurrentHashMap<>();
    private volatile long lastId = 0L;

    @Override
    public CreateResponse create(CreateRequest createRequest) {
        CreateResponse createResponse = new CreateResponse(
                getNewId()
        );
        map.put(createResponse.generatedId(), createResponse);

        return createResponse;
    }

    private synchronized long getNewId() {
        return ++lastId;
    }
}
