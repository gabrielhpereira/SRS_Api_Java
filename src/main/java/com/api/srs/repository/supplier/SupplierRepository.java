package com.api.srs.repository.supplier;

import com.api.srs.dto.supplier.SupplierDto;
import com.api.srs.entity.supplier.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Integer> {
  String SELECT = "SELECT new com.api.srs.dto.supplier.SupplierDto(s.id, s.name, s.address, s.email, s.phone, s.status) FROM SupplierEntity s ";

  @Query(SELECT)
  public List<SupplierDto> listAllSuppliers();

  @Query(SELECT + "WHERE s.status = 1")
  public List<SupplierDto> listAllActiveSuppliers();

  @Query(SELECT
      + "WHERE s.status = :status"
      + " AND (:name IS NULL OR UPPER(s.name) LIKE CONCAT('%', :name, '%'))"
      + " AND (:address IS NULL OR UPPER(s.address) LIKE CONCAT('%', :address, '%'))"
      + " AND (:email IS NULL OR s.email LIKE CONCAT ('%', :email, '%'))"
      + " AND (:phone IS NULL OR s.phone LIKE CONCAT ('%', :phone, '%'))")
  public List<SupplierDto> listSuppliersByFilters(
      @Param("name") String name,
      @Param("address") String address,
      @Param("email") String email,
      @Param("phone") String phone,
      @Param("status") Boolean status
  );
}
