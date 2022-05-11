package com.github.chMatvey.chaosTool.order.web.controller;

import com.github.chMatvey.chaosTool.order.dto.CreateOrderRequest;
import com.github.chMatvey.chaosTool.order.dto.CreateOrderResponse;
import com.github.chMatvey.chaosTool.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/v1/order")
@Slf4j
public record OrderController(OrderService orderService) {
    @PostMapping
    public ResponseEntity<CreateOrderResponse> create(@RequestBody CreateOrderRequest request) {
        log.info("REST request to create new order {}", request);
        return ok(orderService.create(request));
    }
}
