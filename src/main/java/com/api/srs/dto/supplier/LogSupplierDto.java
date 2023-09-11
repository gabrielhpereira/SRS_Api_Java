package com.api.srs.dto.supplier;

public record LogSupplierDto(
    Integer id,
    Integer supplierId,
    String description,
    String date
) {
}
