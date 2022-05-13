package com.github.chMatvey.chaosTool.order.service;

import com.github.chMatvey.chaosTool.clients.delivery.DeliveryClient;
import com.github.chMatvey.chaosTool.clients.delivery.DeliveryRequest;
import com.github.chMatvey.chaosTool.clients.delivery.DeliveryResponse;
import com.github.chMatvey.chaosTool.clients.order.CreateOrderRequest;
import com.github.chMatvey.chaosTool.clients.order.CreateOrderResponse;
import com.github.chMatvey.chaosTool.clients.warehouse.WarehouseClient;
import com.github.chMatvey.chaosTool.clients.warehouse.WarehouseRequest;
import com.github.chMatvey.chaosTool.clients.warehouse.WarehouseResponse;
import com.github.chMatvey.chaosTool.order.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class InMemoryOrderService implements OrderService {
    private final WarehouseClient warehouseClient;
    private final DeliveryClient deliveryClient;

    private final Map<Long, Order> orderMap = new ConcurrentHashMap<>();
    private final AtomicLong lastId = new AtomicLong(0);

    @Override
    public CreateOrderResponse create(CreateOrderRequest request) {
        String productName = request.name();

        WarehouseResponse inStockResponse = warehouseClient.inStock(
                new WarehouseRequest(productName)
        );
        if (!inStockResponse.inStock()) {
            throw new ResponseStatusException(NOT_FOUND, "Product not found on warehouse");
        }

        boolean canDeliver = false;
        try {
            DeliveryResponse deliveryResponse = deliveryClient.canDeliver(
                    new DeliveryRequest(productName)
            );
            canDeliver = deliveryResponse.canDeliver();
        } catch (Exception exception) {
            log.warn("Delivery service return error {}", exception.getMessage());
        }

        long orderId = generateNewId();
        Order order = Order.builder()
                .id(orderId)
                .name(productName)
                .build();
        orderMap.put(order.getId(), order);

        return new CreateOrderResponse(
                order.getId(),
                request.name(),
                canDeliver,
                "Order successfully created"
        );
    }

    private long generateNewId() {
        return lastId.incrementAndGet();
    }
}
