package com.github.chMatvey.chaosTool.order.service;

import com.github.chMatvey.chaosTool.order.web.CreateOrderRequest;
import com.github.chMatvey.chaosTool.order.web.CreateOrderResponse;

public interface OrderService {
    CreateOrderResponse create(CreateOrderRequest request);
}
