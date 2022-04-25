package com.github.chMatvey.chaosTool.chaosClientsStarter;

import com.github.chMatvey.chaosTool.chaosModels.ChaosResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerturbationInjectorImpl implements PerturbationInjector {
    private final List<ErrorGenerator> errorGenerators;

    @Override
    public boolean canInjectError(ChaosResponse chaosResponse) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void injectError(ChaosResponse chaosResponse) {
        throw new RuntimeException("Not implemented");
    }
}
