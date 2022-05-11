package com.github.chMatvey.chaosTool.chaosClientsStarter.error.generator;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
public class ServerErrorGenerator implements ErrorGenerator {
    @Override
    public int statusCode() {
        return 500;
    }

    @Override
    public void generateError() throws RuntimeException {
        throw new ResponseStatusException(INTERNAL_SERVER_ERROR, message());
    }
}
