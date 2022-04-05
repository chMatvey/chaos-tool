package com.github.chMatvey.chaosTool.warehouse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/v1/warehouse")
public class WarehouseController {
    @GetMapping("/{name}")
    ResponseEntity<WarehouseResponse> inStock(@PathVariable String name) {
        return ok(new WarehouseResponse(true, 5));
    }
}
