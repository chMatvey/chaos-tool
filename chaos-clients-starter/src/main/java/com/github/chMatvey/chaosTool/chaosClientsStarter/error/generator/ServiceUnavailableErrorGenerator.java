package com.github.chMatvey.chaosTool.chaosClientsStarter.error.generator;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Component
public class ServiceUnavailableErrorGenerator implements ErrorGenerator {
    @Override
    public int statusCode() {
        return 503;
    }

    @Override
    public void generateError() throws RuntimeException {
        throw new ResponseStatusException(SERVICE_UNAVAILABLE, message());
    }
}
