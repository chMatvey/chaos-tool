package com.github.chMatvey.chaosTool.order.service;

import com.github.chMatvey.chaosTool.clients.order.CreateOrderRequest;
import com.github.chMatvey.chaosTool.clients.order.CreateOrderResponse;

public interface OrderService {
    CreateOrderResponse create(CreateOrderRequest request);
}
