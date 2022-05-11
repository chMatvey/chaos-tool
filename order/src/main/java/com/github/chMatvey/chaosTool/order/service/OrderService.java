package com.github.chMatvey.chaosTool.order.service;

import com.github.chMatvey.chaosTool.order.dto.CreateOrderRequest;
import com.github.chMatvey.chaosTool.order.dto.CreateOrderResponse;

public interface OrderService {
    CreateOrderResponse create(CreateOrderRequest request);
}
