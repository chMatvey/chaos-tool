package com.github.chMatvey.chaosTool.chaosModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WasFaultInjectedResponse {
    private Boolean wasFaultInjected;
    private Integer errorCodeInjected;

    public static WasFaultInjectedResponse noInjected() {
        return new WasFaultInjectedResponse(false, null);
    }
}
