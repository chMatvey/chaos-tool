package com.github.chMatvey.chaosTool.clients.warehouse;

public record WarehouseResponse(
        Boolean inStock,
        Integer count
) {}
