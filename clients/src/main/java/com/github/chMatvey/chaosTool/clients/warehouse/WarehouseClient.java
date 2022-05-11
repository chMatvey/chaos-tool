package com.github.chMatvey.chaosTool.clients.warehouse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "warehouse",
        url = "${clients.warehouse.url}"
)
public interface WarehouseClient {
    @PostMapping("api/v1/warehouse")
    WarehouseResponse inStock(@RequestBody WarehouseRequest request);
}
