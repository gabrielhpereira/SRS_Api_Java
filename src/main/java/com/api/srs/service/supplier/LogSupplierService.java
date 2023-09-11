package com.api.srs.service.supplier;

import com.api.srs.dto.supplier.LogSupplierDto;
import com.api.srs.repository.supplier.LogSupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogSupplierService {

  private final LogSupplierRepository logSupplierRepository;

  public List<LogSupplierDto> listAllLogSupplier(Integer supplierId){
    return this.logSupplierRepository.listAllLogSupplier(supplierId);
  }


}
