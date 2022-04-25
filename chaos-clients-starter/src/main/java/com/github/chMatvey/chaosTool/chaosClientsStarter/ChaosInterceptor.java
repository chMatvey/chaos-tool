package com.github.chMatvey.chaosTool.chaosClientsStarter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ChaosInterceptor implements RequestInterceptor {
    private final ChaosClient chaosClient;
    private final PerturbationInjector perturbationInjector;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Map<String, Collection<String>> headers = requestTemplate.headers();
    }
}
