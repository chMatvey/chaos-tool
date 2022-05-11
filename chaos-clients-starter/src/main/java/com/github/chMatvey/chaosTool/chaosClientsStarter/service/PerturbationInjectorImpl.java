package com.github.chMatvey.chaosTool.chaosClientsStarter.service;

import com.github.chMatvey.chaosTool.chaosClientsStarter.error.generator.ErrorGenerator;
import com.github.chMatvey.chaosTool.chaosModels.ChaosResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
public class PerturbationInjectorImpl implements PerturbationInjector {
    private final Map<Integer, ErrorGenerator> errorGeneratorMap;

    public PerturbationInjectorImpl(List<ErrorGenerator> errorGenerators) {
        this.errorGeneratorMap = errorGenerators.stream().collect(toMap(ErrorGenerator::statusCode, identity()));
    }

    @Override
    public boolean canInjectError(ChaosResponse chaosResponse) {
        return chaosResponse.getInjectError();
    }

    @Override
    public void injectError(ChaosResponse chaosResponse) {
        ErrorGenerator errorGenerator = errorGeneratorMap.get(chaosResponse.getErrorCode());
        errorGenerator.generateError();
    }
}
