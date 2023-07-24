package com.api.srs.entity.employee;

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
public class EmployeeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Integer id;

  @Column(unique = true)
  private String cpf;

  @Column
  private String name;

  @Column
  private String phone;

  @Column
  private String address;

  @Column
  private String email;

  @Column
  private String sector;

  public EmployeeEntity(EmployeeEntity entity) {
    this.id = entity.getId();
    this.cpf = entity.getCpf();
    this.name = entity.getName();
    this.phone = entity.getPhone();
    this.address = entity.getAddress();
    this.email = entity.getEmail();
    this.sector = entity.getSector();
  }
}