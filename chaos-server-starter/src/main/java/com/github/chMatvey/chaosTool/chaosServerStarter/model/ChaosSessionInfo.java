package com.github.chMatvey.chaosTool.chaosServerStarter.model;

import com.github.chMatvey.chaosTool.chaosModels.WasFaultInjectedResponse;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static com.github.chMatvey.chaosTool.chaosModels.WasFaultInjectedResponse.noInjected;

public class ChaosSessionInfo {
    private final Queue<RemoteCallStep> servicesCallSteps = new ConcurrentLinkedQueue<>();

    private final AtomicInteger testCaseCount = new AtomicInteger(1);

    private final AtomicReference<WasFaultInjectedResponse> wasFaultInjected = new AtomicReference<>(noInjected());

    public void addServiceCallStep(RemoteCallStep step) {
        this.servicesCallSteps.add(step);
    }

    public void updateTestCaseCount(RemoteCallStep callStep) {
        this.testCaseCount.addAndGet(callStep.injectedErrorCodes().size());
    }

    public int testCaseCount() {
        return testCaseCount.get();
    }

    public int servicesCallStepsCount() {
        return servicesCallSteps.stream().map(step -> step.injectedErrorCodes().size()).reduce(0, Integer::sum);
    }

    public RemoteCallStep firstCallStep() {
        return servicesCallSteps.peek();
    }

    public void removeFirstCallStepIfErrorCodeExpired() {
        RemoteCallStep callStep = Optional.ofNullable(servicesCallSteps.peek())
                .orElseThrow(() -> new RuntimeException("Session info call steps queue is empty"));
        if (callStep.injectedErrorCodes().isEmpty()) {
            servicesCallSteps.remove();
        }
    }

    public void updateFaultInjectionInfo(int errorCode) {
        this.wasFaultInjected.set(
                new WasFaultInjectedResponse(true, errorCode)
        );
    }

    public void resetFaultInjectionInfo() {
        this.wasFaultInjected.set(noInjected());
    }

    public WasFaultInjectedResponse wasFaultInjected() {
        return wasFaultInjected.get();
    }
}
