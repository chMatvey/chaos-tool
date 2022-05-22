package com.github.chMatvey.chaosTool.chaosTestUtil.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InjectedFaultInfo {
    private List<String> serviceName;
    private List<Integer> errorCode;
}
