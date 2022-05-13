package com.github.chMatvey.chaosTool.chaosServerStarter.util;

import java.util.LinkedList;
import java.util.Queue;

public class ErrorCodeUtil {
    public static Queue<Integer> createDefaultErrorCode() {
        LinkedList<Integer> result = new LinkedList<>();
        result.add(500);
        result.add(503);
        return result;
    }
}
