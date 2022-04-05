package com.github.chMatvey.chaosTool.order.service;

import com.github.chMatvey.chaosTool.order.web.CreateOrderRequest;
import com.github.chMatvey.chaosTool.order.web.CreateOrderResponse;
import com.github.chMatvey.chaosTool.order.model.Order;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryOrderService implements OrderService {
    private final Map<Long, Order> orderMap = new ConcurrentHashMap<>();
    private volatile long lastId;

    @Override
    public CreateOrderResponse create(CreateOrderRequest request) {
        // todo check in stock
        // todo can deliver
        Order order = Order.builder()
                .id(generateId())
                .name(request.name())
                .build();
        orderMap.put(order.getId(), order);

        return new CreateOrderResponse(
                order.getId(),
                request.name(),
                true,
                "Order successfully created"
        );
    }

    private synchronized long generateId() {
        return ++lastId;
    }
}
