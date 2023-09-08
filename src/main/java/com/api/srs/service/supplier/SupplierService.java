package com.api.srs.service.supplier;

import com.api.srs.dto.supplier.SupplierDto;
import com.api.srs.entity.supplier.SupplierEntity;
import com.api.srs.enums.MessageGenericEnum;
import com.api.srs.repository.supplier.SupplierRepository;
import com.api.srs.shared.Validator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {
  private final SupplierRepository supplierRepository;

  @Transactional
  public void enableOrDisableSupplier(Integer id) {
    SupplierEntity supplier = this.supplierRepository.getReferenceById(id);

    supplier.setStatus(!supplier.getStatus());

    this.supplierRepository.save(supplier);
  }

  @Transactional
  public void saveOrUpdateSupplier(SupplierDto supplierDto) {

  }

  private void saveSupplier(SupplierDto supplierDto) {

  }

  private void updateSupplier(SupplierDto supplierDto) {

  }

  private static void validateSupplierDto(SupplierDto supplierDto) {
    if (supplierDto.name() == null || supplierDto.name().isBlank())
      throw new ValidationException(MessageGenericEnum.NAME_NULL_OR_EMPTY.getMessage());

    if (supplierDto.email() == null || supplierDto.email().isBlank())
      throw new ValidationException(MessageGenericEnum.EMAIL_NULL_OR_EMPTY.getMessage());

    if (supplierDto.phone() == null || supplierDto.phone().isBlank())
      throw new ValidationException(MessageGenericEnum.PHONE_NULL_OR_EMPTY.getMessage());

    if (supplierDto.address() == null || supplierDto.address().isBlank())
      throw new ValidationException(MessageGenericEnum.ADDRESS_NULL_OR_EMPTY.getMessage());
  }

  public List<SupplierDto> listAllSuppliers() {
    List<SupplierDto> listVo = this.supplierRepository.listAllSuppliers();

    if (listVo.isEmpty()) throw new ValidationException(MessageGenericEnum.NOT_FOUND.getMessage());

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

    if (listVo.isEmpty()) throw new ValidationException(MessageGenericEnum.NOT_FOUND.getMessage());

    return listVo;
  }
}
