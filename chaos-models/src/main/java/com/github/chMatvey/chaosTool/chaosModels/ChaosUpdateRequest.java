package com.github.chMatvey.chaosTool.chaosModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChaosUpdateRequest {
    private Integer sessionId;
    private Integer testCaseId;
    private Integer testCaseStep;
    private String sourceServiceName;
    private String targetServiceName;
    private ServiceRole serviceRole;
    private String uri;
    private String method;
}
