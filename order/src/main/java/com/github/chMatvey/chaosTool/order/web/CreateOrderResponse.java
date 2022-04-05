package com.github.chMatvey.chaosTool.order.web;

public record CreateOrderResponse(
        Long id,
        String name,
        Boolean canDeliver,
        String message
) {
}
