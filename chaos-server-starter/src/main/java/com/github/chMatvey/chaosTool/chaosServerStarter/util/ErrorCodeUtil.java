package com.github.chMatvey.chaosTool.chaosServerStarter.util;

import java.util.LinkedList;
import java.util.Queue;

public class ErrorCodeUtil {
    public static final Queue<Integer> DEFAULT_ERROR_CODES;

    static {
        DEFAULT_ERROR_CODES = new LinkedList<>();
        DEFAULT_ERROR_CODES.add(500);
        DEFAULT_ERROR_CODES.add(503);
    }
}
