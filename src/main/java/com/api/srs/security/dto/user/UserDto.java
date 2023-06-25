package com.api.srs.security.dto.user;

public record UserDto(
    Integer id,
    String firstname,
    String lastname,
    String email,
    String password
) {
  public UserDto(Integer id, String firstname, String lastname, String email) {
    this(id, firstname, lastname, email, null);
  }
}