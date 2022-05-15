package com.github.chMatvey.chaosTool.order.service;

import com.github.chMatvey.chaosTool.clients.order.CreateOrderRequest;
import com.github.chMatvey.chaosTool.clients.order.CreateOrderResponse;
import com.github.chMatvey.chaosTool.order.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class InMemoryOrderService implements OrderService {
    private final RemoteCallService remoteCallService;

    private final Map<Long, Order> orderMap = new ConcurrentHashMap<>();
    private final AtomicLong lastId = new AtomicLong(0);

    @SneakyThrows
    @Override
    public CreateOrderResponse create(CreateOrderRequest request) {
        CompletableFuture<Boolean> isStockFuture = remoteCallService.inStock(request.name());
        CompletableFuture<Boolean> canDeliverFuture = remoteCallService.canDeliver(request.name());

        if (!isStockFuture.get()) {
            throw new ResponseStatusException(NOT_FOUND, "Product not found on warehouse");
        }

        Order order = Order.builder()
                .id(lastId.incrementAndGet())
                .name(request.name())
                .build();
        orderMap.put(order.getId(), order);

        return new CreateOrderResponse(
                order.getId(),
                request.name(),
                canDeliverFuture.get(),
                "Order successfully created"
        );
    }
}
