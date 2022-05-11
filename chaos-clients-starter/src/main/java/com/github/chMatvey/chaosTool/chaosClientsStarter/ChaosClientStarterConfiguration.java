package com.github.chMatvey.chaosTool.chaosClientsStarter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("com.github.chMatvey.chaosTool.chaosClientsStarter")
public class ChaosClientStarterConfiguration {
    public static final String CHAOS_SESSION_ID_HEADER = "chaos-session-id";
    public static final String CHAOS_TEST_CASE_ID_HEADER = "chaos-test-case-id";
    public static final String CHAOS_TEST_CASE_STEP_HEADER = "chaos-test-case-step";
    public static final String CHAOS_SENDER_SERVICE_HEADER = "chaos-sender-service";

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
