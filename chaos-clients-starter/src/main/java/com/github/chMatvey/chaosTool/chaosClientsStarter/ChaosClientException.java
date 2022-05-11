package com.github.chMatvey.chaosTool.chaosClientsStarter;

public class ChaosClientException extends RuntimeException {
    public ChaosClientException(String message) {
        super("Chaos Client Exception!\n" +
                "This exception is internal error, please contact to support.\n" +
                "Details: " + message);
    }

    public ChaosClientException(String message, Throwable cause) {
        super("Chaos Client Exception!\n" +
                "This exception is internal error, please contact to support.\n" +
                "Details: " + message, cause);
    }
}
