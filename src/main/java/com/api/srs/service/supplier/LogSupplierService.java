package com.api.srs.service.supplier;

import static com.api.srs.shared.GenericLog.*;

import com.api.srs.dto.supplier.LogSupplierDto;
import com.api.srs.entity.supplier.LogSupplierEntity;
import com.api.srs.entity.supplier.SupplierEntity;
import com.api.srs.repository.supplier.LogSupplierRepository;
import com.api.srs.shared.DateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogSupplierService {

  private final LogSupplierRepository logSupplierRepository;

  public List<LogSupplierDto> listAllLogSupplier(Integer supplierId) {
    return this.logSupplierRepository.listAllLogSupplier(supplierId);
  }

  public void saveLogUpdateSupplier(SupplierEntity newSupplier, SupplierEntity oldSupplier) {
    StringBuilder sb = new StringBuilder();

    sb.append(updateEntity(newSupplier.getName(), oldSupplier.getName(), "Name"));
    sb.append(updateEntity(newSupplier.getEmail(), oldSupplier.getEmail(), "Email"));
    sb.append(updateEntity(newSupplier.getAddress(), oldSupplier.getAddress(), "Address"));
    sb.append(updateEntity(newSupplier.getPhone(), oldSupplier.getPhone(), "Phone"));
    sb.append(updateEntity(newSupplier.getStatus(), oldSupplier.getStatus(), "Status"));

    if (!sb.isEmpty())
      this.logSupplierRepository.saveAndFlush(
          LogSupplierEntity
              .builder()
              .supplierId(newSupplier.getId())
              .description("Supplier " + newSupplier.getId() + " : \n\n" + sb)
              .date(DateTime.nowDate())
              .build()
      );
  }

  public void saveLogNewSupplier(SupplierEntity supplier) {
    this.logSupplierRepository.saveAndFlush(
        LogSupplierEntity
            .builder()
            .supplierId(supplier.getId())
            .description("Supplier " + supplier.getId() + " - " + supplier.getName() + " has been created!")
            .date(DateTime.nowDate())
            .build()
    );
  }

  public void saveLogDeleteSupplier(SupplierEntity supplier) {
    this.logSupplierRepository.saveAndFlush(
        LogSupplierEntity
            .builder()
            .supplierId(supplier.getId())
            .description("Supplier " + supplier.getId() + " - " + supplier.getName() + " has been deleted!")
            .date(DateTime.nowDate())
            .build()
    );
  }
}
