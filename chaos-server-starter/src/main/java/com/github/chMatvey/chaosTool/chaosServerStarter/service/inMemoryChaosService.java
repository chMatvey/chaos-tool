package com.github.chMatvey.chaosTool.chaosServerStarter.service;

import com.github.chMatvey.chaosTool.chaosModels.ChaosCreateRequest;
import com.github.chMatvey.chaosTool.chaosModels.ChaosResponse;
import com.github.chMatvey.chaosTool.chaosModels.ChaosUpdateRequest;
import com.github.chMatvey.chaosTool.chaosServerStarter.model.ChaosSessionInfo;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.chMatvey.chaosTool.chaosServerStarter.util.RemoteCallStepUtil.fromCreateRequest;
import static com.github.chMatvey.chaosTool.chaosServerStarter.util.RemoteCallStepUtil.fromUpdateRequest;

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
        ChaosResponse createResponse = ChaosResponse.builder()
                .sessionId(sessionId)
                .testCaseId(testCaseId)
                .testCaseStep(1)
                .injectError(false)
                .build();

        if (isFirstTestCase(testCaseId)) {
            ChaosSessionInfo sessionInfo = new ChaosSessionInfo();
            sessionInfo.addServiceCallStep(fromCreateRequest(createRequest));
            chaosSessions.put(createResponse.getSessionId(), sessionInfo);
        }

        return createResponse;
    }

    @Override
    public ChaosResponse update(ChaosUpdateRequest updateRequest) {
        if (isFirstTestCase(updateRequest.getTestCaseId())) {
            ChaosSessionInfo sessionInfo = chaosSessions.get(updateRequest.getSessionId());
            sessionInfo.addServiceCallStep(fromUpdateRequest(updateRequest));
        }

        return ChaosResponse.builder()
                .sessionId(updateRequest.getSessionId())
                .testCaseId(updateRequest.getTestCaseId())
                .testCaseStep(updateRequest.getTestCaseStep() + 1)
                .injectError(false)
                .build();
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
