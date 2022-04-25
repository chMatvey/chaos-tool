package com.github.chMatvey.chaosTool.chaosClientsStarter;

public interface ErrorGenerator {
    int statusCode();

    void generateError() throws RuntimeException;
}
