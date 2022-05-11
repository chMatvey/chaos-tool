package com.github.chMatvey.chaosTool.chaosModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChaosResponse {
    private Integer sessionId;
    private Integer testCaseId;
    private Integer testCaseStep;
    private Boolean injectError;
    private Integer errorCode;
}
