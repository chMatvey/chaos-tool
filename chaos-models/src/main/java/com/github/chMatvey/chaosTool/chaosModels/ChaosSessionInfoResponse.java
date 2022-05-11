package com.github.chMatvey.chaosTool.chaosModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChaosSessionInfoResponse {
    private Integer testCaseCount;
}
