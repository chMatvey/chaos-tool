package com.github.chMatvey.chaosTool.chaosServerStarter.util;

import com.github.chMatvey.chaosTool.chaosModels.ChaosSessionInfoResponse;
import com.github.chMatvey.chaosTool.chaosServerStarter.model.ChaosSessionInfo;

public class ChaosSessionInfoUtil {
    public static ChaosSessionInfoResponse toResponse(ChaosSessionInfo entity) {
        return entity == null ? null : ChaosSessionInfoResponse.builder()
                .testCaseCount(entity.testCaseCount())
                .build();
    }
}
