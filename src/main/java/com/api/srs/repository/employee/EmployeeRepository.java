package com.api.srs.repository.employee;

import com.api.srs.dto.employee.EmployeeDto;
import com.api.srs.entity.employee.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
  String SELECT = "SELECT new com.api.srs.dto.employee.EmployeeDto(e.id, e.cpf, e.name, e.sector, e.phone, e.address, e.email) FROM EmployeeEntity e";

  @Query(SELECT)
  public List<EmployeeDto> listAllEmployees();

  @Query(SELECT
      + " WHERE 1 = 1"
      + "     AND (:cpf IS NULL OR e.cpf LIKE CONCAT ('%', :cpf, '%'))"
      + "     AND (:name IS NULL OR UPPER(e.name) LIKE CONCAT('%', UPPER(:name), '%'))"
      + "     AND (:sector IS NULL OR UPPER(e.sector) LIKE CONCAT('%', UPPER(:sector), '%'))"
      + "     AND (:phone IS NULL OR e.phone LIKE CONCAT('%', :phone, '%'))"
      + "     AND (:address IS NULL OR UPPER(e.address) LIKE CONCAT('%', UPPER(:address), '%'))"
      + "     AND (:email IS NULL OR e.email LIKE CONCAT('%', :email, '%'))")
  public List<EmployeeDto> listEmployeeByFilters(
      @Param("cpf") String cpf,
      @Param("name") String name,
      @Param("sector") String sector,
      @Param("phone") String phone,
      @Param("address") String address,
      @Param("email") String email
  );
}
