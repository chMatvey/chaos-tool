package com.github.chMatvey.chaosTool.warehouse;

public record WarehouseResponse(
        Boolean inStock,
        Integer count
) {
}
