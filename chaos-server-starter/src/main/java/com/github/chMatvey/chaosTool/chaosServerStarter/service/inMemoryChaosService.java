package com.github.chMatvey.chaosTool.chaosServerStarter.service;

import com.github.chMatvey.chaosTool.chaosModels.*;
import com.github.chMatvey.chaosTool.chaosServerStarter.model.ChaosSessionInfo;
import com.github.chMatvey.chaosTool.chaosServerStarter.model.RemoteCallStep;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.chMatvey.chaosTool.chaosModels.ServiceRole.SENDER;
import static com.github.chMatvey.chaosTool.chaosServerStarter.util.ChaosSessionInfoUtil.toResponse;
import static com.github.chMatvey.chaosTool.chaosServerStarter.util.RemoteCallStepUtil.fromCreateRequest;
import static com.github.chMatvey.chaosTool.chaosServerStarter.util.RemoteCallStepUtil.fromUpdateRequest;
import static java.util.Optional.ofNullable;

@Service
public class inMemoryChaosService implements ChaosService {
    private final Map<Integer, ChaosSessionInfo> chaosSessions = new ConcurrentHashMap<>();
    private final AtomicInteger lastId = new AtomicInteger(0);
    public static final int FIRST_TEST_CASE_ID = 1;

    @Override
    public ChaosResponse create(ChaosCreateRequest createRequest) {
        boolean hasSessionIdHeader = createRequest.getSessionId() != null;
        int sessionId = hasSessionIdHeader ? createRequest.getSessionId() : generateNewSessionId();
        int testCaseId = hasSessionIdHeader ? getTestCaseId(sessionId) : FIRST_TEST_CASE_ID;
        boolean injectError = false;
        Integer errorCode = null;

        if (isFirstTestCase(testCaseId)) {
            ChaosSessionInfo sessionInfo = new ChaosSessionInfo();
            RemoteCallStep callStep = fromCreateRequest(createRequest);
            sessionInfo.addServiceCallStep(callStep);
            sessionInfo.updateTestCaseCount(callStep);
            chaosSessions.put(sessionId, sessionInfo);
        } else {
            ChaosSessionInfo sessionInfo = chaosSessions.get(sessionId);
            RemoteCallStep callStep = sessionInfo.firstCallStep();
            if (callStep.equalCurrentRequest(createRequest)) {
                injectError = true;
                errorCode = Optional.ofNullable(callStep.injectedErrorCodes().poll())
                        .orElseThrow(() -> new RuntimeException("Cannot extract error code from Chaos session info"));
                sessionInfo.removeFirstCallStepIfErrorCodeExpired();
                sessionInfo.updateFaultInjectionInfo(errorCode, createRequest.getTargetServiceName(), testCaseId);
            }
        }

        return ChaosResponse.builder()
                .sessionId(sessionId)
                .testCaseId(testCaseId)
                .testCaseStep(1)
                .injectError(injectError)
                .errorCode(errorCode)
                .build();
    }

    @Override
    public ChaosResponse update(ChaosUpdateRequest updateRequest) {
        boolean injectError = false;
        Integer errorCode = null;
        ChaosSessionInfo sessionInfo = chaosSessions.get(updateRequest.getSessionId());

        if (updateRequest.getServiceRole() == SENDER) {
            if (isFirstTestCase(updateRequest.getTestCaseId())) {
                RemoteCallStep callStep = fromUpdateRequest(updateRequest);
                sessionInfo.addServiceCallStep(callStep);
                sessionInfo.updateTestCaseCount(callStep);
            } else {
                RemoteCallStep callStep = sessionInfo.firstCallStep();
                WasFaultInjectedResponse wasFaultInjected = sessionInfo.wasFaultInjected();
                if (callStep.equalCurrentRequest(updateRequest) &&
                        !Objects.equals(wasFaultInjected.getTestCaseId(), updateRequest.getTestCaseId())) {
                    injectError = true;
                    errorCode = Optional.ofNullable(callStep.injectedErrorCodes().poll())
                            .orElseThrow(() -> new RuntimeException("Cannot extract error code from Chaos session info"));
                    sessionInfo.removeFirstCallStepIfErrorCodeExpired();
                    sessionInfo.updateFaultInjectionInfo(errorCode, updateRequest.getTargetServiceName(), updateRequest.getTestCaseId());
                }
            }
        }

        return ChaosResponse.builder()
                .sessionId(updateRequest.getSessionId())
                .testCaseId(updateRequest.getTestCaseId())
                .testCaseStep(updateRequest.getTestCaseStep() + 1)
                .injectError(injectError)
                .errorCode(errorCode)
                .build();
    }

    @Override
    public Optional<ChaosSessionInfoResponse> get(Integer id) {
        return ofNullable(toResponse(chaosSessions.get(id)));
    }

    @Override
    public Optional<WasFaultInjectedResponse> wasFaultInjected(Integer chaosSessionId) {
        ChaosSessionInfo chaosSessionInfo = chaosSessions.get(chaosSessionId);
        if (chaosSessionInfo == null) {
            return Optional.empty();
        } else {
            return Optional.of(chaosSessionInfo.wasFaultInjected());
        }
    }

    private int generateNewSessionId() {
        return lastId.incrementAndGet();
    }

    private int getTestCaseId(int sessionId) {
        ChaosSessionInfo sessionInfo = chaosSessions.get(sessionId);
        return sessionInfo.testCaseCount() - sessionInfo.servicesCallStepsCount() + FIRST_TEST_CASE_ID;
    }

    private boolean isFirstTestCase(int testCaseId) {
        return testCaseId == FIRST_TEST_CASE_ID;
    }
}
