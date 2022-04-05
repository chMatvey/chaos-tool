package com.github.chMatvey.chaosTool.chaosClientsStarter;

import org.springframework.stereotype.Component;

@Component
public class ServerErrorGenerator implements ErrorGenerator {
    @Override
    public int statusCode() {
        return 500;
    }

    @Override
    public void generateError() throws RuntimeException {
        throw new RuntimeException("Not implemented");
    }
}
