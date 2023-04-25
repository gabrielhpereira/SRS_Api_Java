package com.api.srs.dto.employee;

public record EmployeeDto(
        Integer id,
        String cpf,
        String name,
        String phone,
        String email,
        String address,
        String sector
) { }
