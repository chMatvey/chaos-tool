package com.github.chMatvey.chaosTool.chaosTestUtil;

import static java.util.Optional.ofNullable;

public class Util {
    static <T> T ofNullableOrThrowError(T object, String errorMessage) {
        return ofNullable(object)
                .orElseThrow(() -> new ChaosTestUtilException(errorMessage));
    }
}
