package com.github.chMatvey.chaosTool.clients.delivery;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "delivery",
        url = "${clients.delivery.url}"
)
public interface DeliveryClient {
    @PostMapping("api/v1/delivery")
    DeliveryResponse canDeliver(@RequestBody DeliveryRequest request);
}
