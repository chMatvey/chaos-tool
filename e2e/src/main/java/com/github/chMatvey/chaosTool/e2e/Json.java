package com.github.chMatvey.chaosTool.e2e;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Json {
    private final Object object;

    public boolean hasField(String fieldName) {
        throw new RuntimeException("Not Implemented");
    }

    public String getStringFieldValue(String fieldName) {
        throw new RuntimeException("Not Implemented");
    }

    public Boolean getBooleanFieldValue(String fieladName) {
        throw new RuntimeException("Not Implemented");
    }
}
