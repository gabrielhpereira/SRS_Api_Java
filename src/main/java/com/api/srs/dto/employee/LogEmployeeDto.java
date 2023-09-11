package com.api.srs.dto.employee;

public record LogEmployeeDto(
    Integer id,
    Integer employeeId,
    String description,
    String date
) {
}
