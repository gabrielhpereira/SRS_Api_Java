package com.api.srs.service.supplier;

import com.api.srs.dto.supplier.SupplierDto;
import com.api.srs.enums.employee.MessageEmployeeEnum;
import com.api.srs.repository.supplier.SupplierRepository;
import com.api.srs.shared.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {
  private final SupplierRepository supplierRepository;

  public List<SupplierDto> listAllSuppliers() {
    List<SupplierDto> listVo = this.supplierRepository.listAllSuppliers();

    if (listVo.isEmpty()) throw new ValidationException(MessageEmployeeEnum.NOT_FOUND.getMessage());

    return listVo;
  }


  public List<SupplierDto> listSuppliersByFilters(SupplierDto supplierDto) {
    List<SupplierDto> listVo = this.supplierRepository.listSuppliersByFilters(
        Validator.validateStringNullOrEmpty(supplierDto.name()),
        Validator.validateStringNullOrEmpty(supplierDto.address()),
        Validator.validateStringNullOrEmpty(supplierDto.email()),
        Validator.validateStringNullOrEmpty(supplierDto.phone()),
        supplierDto.status().equals("1")
    );

    if (listVo.isEmpty()) throw new ValidationException(MessageEmployeeEnum.NOT_FOUND.getMessage());

    return listVo;
  }
}
