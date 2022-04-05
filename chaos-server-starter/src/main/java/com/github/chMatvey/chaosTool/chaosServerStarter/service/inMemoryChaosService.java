package com.github.chMatvey.chaosTool.chaosServerStarter.service;

import com.github.chMatvey.chaosTool.chaosModels.ChaosResponse;
import com.github.chMatvey.chaosTool.chaosModels.CreateRequest;
import com.github.chMatvey.chaosTool.chaosModels.UpdateRequest;
import com.github.chMatvey.chaosTool.chaosServerStarter.model.RemoteCallStep;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.List.of;

@Service
public class inMemoryChaosService implements ChaosService {
    private final Map<Long, List<RemoteCallStep>> map = new ConcurrentHashMap<>();
    private volatile long lastId = 0L;

    @Override
    public ChaosResponse create(CreateRequest createRequest) {
        ChaosResponse createResponse = new ChaosResponse(
                getNewId(),
                1,
                false,
                503
        );
        map.put(createResponse.generatedId(), new ArrayList<>(of(new RemoteCallStep())));

        return createResponse;
    }

    @Override
    public ChaosResponse update(UpdateRequest updateRequest) {
        throw new RuntimeException("Not implemented");
    }

    private synchronized long getNewId() {
        return ++lastId;
    }
}
