package com.github.chMatvey.chaosTool.chaosClientsStarter;

import com.github.chMatvey.chaosTool.chaosClientsStarter.service.interceptor.ChaosServletInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class ChaosClientWebMvcConfigurer implements WebMvcConfigurer {
    private final ChaosServletInterceptor chaosClientInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(chaosClientInterceptor);
    }
}
