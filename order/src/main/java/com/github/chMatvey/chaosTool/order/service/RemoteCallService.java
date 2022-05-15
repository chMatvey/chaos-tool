package com.github.chMatvey.chaosTool.order.service;

import com.github.chMatvey.chaosTool.clients.delivery.DeliveryClient;
import com.github.chMatvey.chaosTool.clients.delivery.DeliveryRequest;
import com.github.chMatvey.chaosTool.clients.delivery.DeliveryResponse;
import com.github.chMatvey.chaosTool.clients.warehouse.WarehouseClient;
import com.github.chMatvey.chaosTool.clients.warehouse.WarehouseRequest;
import com.github.chMatvey.chaosTool.clients.warehouse.WarehouseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class RemoteCallService {
    private final WarehouseClient warehouseClient;
    private final DeliveryClient deliveryClient;

    @Async
    public CompletableFuture<Boolean> inStock(String name) {
        WarehouseRequest request = new WarehouseRequest(name);
        WarehouseResponse response = warehouseClient.inStock(request);
        return completedFuture(response.inStock());
    }

    @Async
    public CompletableFuture<Boolean> canDeliver(String name) {
        DeliveryRequest request = new DeliveryRequest(name);
        try {
            DeliveryResponse response = deliveryClient.canDeliver(request);
            return completedFuture(response.canDeliver());
        } catch (Exception e) {
            log.warn("Delivery service return error {}", e.getMessage());
            return completedFuture(false);
        }
    }
}
