package com.github.chMatvey.chaosTool.chaosServerStarter.service;

import com.github.chMatvey.chaosTool.chaosModels.ChaosCreateRequest;
import com.github.chMatvey.chaosTool.chaosModels.ChaosResponse;
import com.github.chMatvey.chaosTool.chaosModels.ChaosSessionInfoResponse;
import com.github.chMatvey.chaosTool.chaosModels.ChaosUpdateRequest;
import com.github.chMatvey.chaosTool.chaosServerStarter.model.ChaosSessionInfo;
import com.github.chMatvey.chaosTool.chaosServerStarter.model.RemoteCallStep;
import org.springframework.stereotype.Service;

import java.util.Map;
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
            sessionInfo.addServiceCallStep(fromCreateRequest(createRequest));
            sessionInfo.incrementTestCaseCount();
            chaosSessions.put(sessionId, sessionInfo);
        } else {
            ChaosSessionInfo sessionInfo = chaosSessions.get(sessionId);
            RemoteCallStep callStep = sessionInfo.firstCallStep();
            if (callStep.equalCurrentRequest(createRequest)) {
                injectError = true;
                errorCode = callStep.injectedErrorCode().poll();
                sessionInfo.removeFirstCallStepIfErrorCodeExpired();
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

        if (isFirstTestCase(updateRequest.getTestCaseId()) && updateRequest.getServiceRole() == SENDER) {
            sessionInfo.addServiceCallStep(fromUpdateRequest(updateRequest));
            sessionInfo.incrementTestCaseCount();
        } else {
            RemoteCallStep callStep = sessionInfo.firstCallStep();
            if (callStep.equalCurrentRequest(updateRequest)) {
                injectError = true;
                errorCode = callStep.injectedErrorCode().poll();
                sessionInfo.removeFirstCallStepIfErrorCodeExpired();
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

    private int generateNewSessionId() {
        return lastId.incrementAndGet();
    }

    private int getTestCaseId(int sessionId) {
        ChaosSessionInfo sessionInfo = chaosSessions.get(sessionId);
        int firstErrorInjectionTestCaseId = FIRST_TEST_CASE_ID + 1;
        return sessionInfo.testCaseCount() - sessionInfo.servicesCallStepsCount() + firstErrorInjectionTestCaseId;
    }

    private boolean isFirstTestCase(int testCaseId) {
        return testCaseId == FIRST_TEST_CASE_ID;
    }
}
