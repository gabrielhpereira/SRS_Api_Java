package com.api.srs.security.dto.user;

public record UserDto(
        Integer id,
        String firstname,
        String lastname,
        String email,
        String password
) { }