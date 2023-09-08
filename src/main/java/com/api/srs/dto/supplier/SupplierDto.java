package com.api.srs.dto.supplier;

public record SupplierDto(
    Integer id,
    String name,
    String address,
    String email,
    String phone,
    String status
) {
  public SupplierDto(Integer id, String name, String address, String email, String phone, Boolean isStatus) {
    this(id, name, address, email, phone, isStatus ? "Active" : "Disabled");
  }
}
