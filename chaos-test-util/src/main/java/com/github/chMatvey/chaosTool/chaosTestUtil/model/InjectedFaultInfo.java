package com.github.chMatvey.chaosTool.chaosTestUtil.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InjectedFaultInfo {
    private String serviceName;
    private Integer errorCode;
}
