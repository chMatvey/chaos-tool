package com.github.chMatvey.chaosTool.chaosModels;

public record UpdateRequest(String sourceServiceName,
                            Integer resultStatusCode) {
}
