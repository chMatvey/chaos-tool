package com.github.chMatvey.chaosTool.chaosClientsStarter.error.generator;

public interface ErrorGenerator {
    int statusCode();

    void generateError() throws RuntimeException;

    default String message() {
        return "The error generated by chaos tool";
    }
}
