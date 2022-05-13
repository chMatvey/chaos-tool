package com.github.chMatvey.chaosTool.chaosTestUtil;

public class ChaosTestUtilException extends RuntimeException {
    public ChaosTestUtilException(String message) {
        super("Chaos Test Util Exception!\n" +
                "This exception is internal error, please contact to support.\n" +
                "Details: " + message);
    }

    public ChaosTestUtilException(String message, Throwable cause) {
        super("Chaos Test Util Exception!\n" +
                "This exception is internal error, please contact to support.\n" +
                "Details: " + message, cause);
    }
}
