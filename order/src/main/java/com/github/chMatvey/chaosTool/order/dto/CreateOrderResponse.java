package com.github.chMatvey.chaosTool.order.dto;

public record CreateOrderResponse(
        Long id,
        String name,
        Boolean canDeliver,
        String message
) {}
