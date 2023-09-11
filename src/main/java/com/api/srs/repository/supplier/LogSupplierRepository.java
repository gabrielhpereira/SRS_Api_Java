package com.api.srs.repository.supplier;

import com.api.srs.dto.supplier.LogSupplierDto;
import com.api.srs.entity.supplier.LogSupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogSupplierRepository extends JpaRepository<LogSupplierEntity, Integer> {

  @Query("SELECT new com.api.srs.dto.supplier.LogSupplierDto(l.id, l.supplierId, l.description, l.date)"
      + "     FROM LogSupplierEntity l"
      + " WHERE l.supplierId = :supplierId")
  public List<LogSupplierDto> listAllLogSupplier(
      @Param("supplierId") Integer supplierId
  );
}
