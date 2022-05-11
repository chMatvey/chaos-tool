package com.github.chMatvey.chaosTool.chaosServerStarter.model;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ChaosSessionInfo {
    private final Queue<RemoteCallStep> servicesCallSteps = new ConcurrentLinkedQueue<>();

    private final AtomicInteger testCaseCount = new AtomicInteger(1);

    public void addServiceCallStep(RemoteCallStep step) {
        this.servicesCallSteps.add(step);
    }

    public void incrementTestCaseCount() {
        this.testCaseCount.incrementAndGet();
    }

    public int testCaseCount() {
        return testCaseCount.get();
    }

    public int servicesCallStepsCount() {
        return servicesCallSteps.size();
    }
}
