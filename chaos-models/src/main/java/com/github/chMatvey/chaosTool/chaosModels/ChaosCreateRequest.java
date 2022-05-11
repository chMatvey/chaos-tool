package com.github.chMatvey.chaosTool.chaosModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChaosCreateRequest {
    private Integer sessionId;
    private String sourceServiceName;
    private String targetServiceName;
    private ServiceRole serviceRole;
    private String uri;
    private String method;
}
