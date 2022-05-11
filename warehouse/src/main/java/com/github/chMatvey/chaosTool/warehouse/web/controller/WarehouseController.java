package com.github.chMatvey.chaosTool.warehouse.web.controller;

import com.github.chMatvey.chaosTool.clients.warehouse.WarehouseRequest;
import com.github.chMatvey.chaosTool.clients.warehouse.WarehouseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/v1/warehouse")
@Slf4j
public class WarehouseController {
    @PostMapping
    ResponseEntity<WarehouseResponse> inStock(@RequestBody WarehouseRequest request) {
        log.info("REST request to check stock availability {}", request);
        return ok(new WarehouseResponse(true, 5));
    }
}
