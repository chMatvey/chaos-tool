package com.github.chMatvey.chaosTool.chaosServerStarter.model;

import java.util.Optional;
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

    public RemoteCallStep firstCallStep() {
        return servicesCallSteps.peek();
    }

    public void removeFirstCallStepIfErrorCodeExpired() {
        RemoteCallStep callStep = Optional.ofNullable(servicesCallSteps.peek())
                .orElseThrow(() -> new RuntimeException("Session info call steps queue is empty"));
        if (callStep.injectedErrorCode().isEmpty()) {
            servicesCallSteps.remove();
        }
    }
}
