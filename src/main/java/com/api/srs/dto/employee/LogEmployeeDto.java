package com.api.srs.dto.employee;

public record LogEmployeeDto(
    Integer id,
    Integer idEmployee,
    String description,
    String date
) {
}
