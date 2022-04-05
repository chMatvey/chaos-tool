package com.github.chMatvey.chaosTool.delivery;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/v1/delivery")
public class DeliveryController {
    @GetMapping("/{name}")
    ResponseEntity<DeliveryResponse> canDeliver(@PathVariable String name) {
        return ok(new DeliveryResponse(true));
    }
}
