package com.github.chMatvey.chaosTool.clients.order;

public record CreateOrderResponse(
        Long id,
        String name,
        Boolean canDeliver,
        String message
) {}
