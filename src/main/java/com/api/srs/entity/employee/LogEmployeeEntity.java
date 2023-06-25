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
public class LogEmployeeEntity {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column
  private Integer employeeId;

  @Column
  private String description;

  @Column
  private String date;
}
