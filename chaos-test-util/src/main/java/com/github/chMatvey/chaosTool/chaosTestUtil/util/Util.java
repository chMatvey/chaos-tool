package com.github.chMatvey.chaosTool.chaosTestUtil.util;

import com.github.chMatvey.chaosTool.chaosTestUtil.model.ChaosTestUtilException;

import static java.util.Optional.ofNullable;

public class Util {
    public static <T> T ofNullableOrThrowError(T object, String errorMessage) {
        return ofNullable(object)
                .orElseThrow(() -> new ChaosTestUtilException(errorMessage));
    }
}
