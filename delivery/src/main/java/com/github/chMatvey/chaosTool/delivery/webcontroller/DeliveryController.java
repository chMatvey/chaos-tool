package com.github.chMatvey.chaosTool.delivery.webcontroller;

import com.github.chMatvey.chaosTool.clients.delivery.DeliveryRequest;
import com.github.chMatvey.chaosTool.clients.delivery.DeliveryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/v1/delivery")
@Slf4j
public class DeliveryController {
    @PostMapping
    ResponseEntity<DeliveryResponse> canDeliver(@RequestBody DeliveryRequest request) {
        log.info("REST request to check delivery availability {}", request);
        return ok(new DeliveryResponse(true));
    }
}
