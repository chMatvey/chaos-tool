package com.github.chMatvey.chaosTool.chaosServerStarter.model;

import com.github.chMatvey.chaosTool.chaosModels.ServiceRole;

public record RemoteCallStep(
        String sourceServiceName,
        String targetServiceName,
        ServiceRole serviceRole,
        String uri,
        String method
) {}
