package com.api.srs.entity.supplier;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class SupplierEntity {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column
  private String name;

  @Column
  private String address;

  @Column
  private String email;

  @Column
  private String phone;

  @Column
  private Boolean status;

  public SupplierEntity(SupplierEntity supplier){
    this.id = supplier.getId();
    this.name = supplier.getName();
    this.address = supplier.getAddress();
    this.email = supplier.getEmail();
    this.phone = supplier.getPhone();
    this.status = supplier.getStatus();
  }
}
