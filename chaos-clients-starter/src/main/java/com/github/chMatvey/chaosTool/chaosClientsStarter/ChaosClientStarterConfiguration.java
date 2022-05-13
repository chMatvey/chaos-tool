package com.github.chMatvey.chaosTool.chaosClientsStarter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("com.github.chMatvey.chaosTool.chaosClientsStarter")
public class ChaosClientStarterConfiguration {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
