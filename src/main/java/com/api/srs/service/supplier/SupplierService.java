package com.api.srs.service.supplier;

import com.api.srs.dto.supplier.SupplierDto;
import com.api.srs.entity.supplier.SupplierEntity;
import com.api.srs.enums.MessageGenericEnum;
import com.api.srs.repository.supplier.SupplierRepository;

import static com.api.srs.shared.Validator.*;

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
    validateSupplierDto(supplierDto);

    if (validateIntegerNullOrZero(supplierDto.id()) == null) this.saveSupplier(supplierDto);
    else this.updateSupplier(supplierDto);
  }

  private void saveSupplier(SupplierDto supplierDto) {

  }

  private void updateSupplier(SupplierDto supplierDto) {

  }

  private static void validateSupplierDto(SupplierDto supplierDto) {
    if (validateStringNullOrEmpty(supplierDto.name()) == null)
      throw new ValidationException(MessageGenericEnum.NAME_NULL_OR_EMPTY.getMessage());

    if (validateStringNullOrEmpty(supplierDto.email()) == null)
      throw new ValidationException(MessageGenericEnum.EMAIL_NULL_OR_EMPTY.getMessage());

    if (validateStringNullOrEmpty(supplierDto.phone()) == null)
      throw new ValidationException(MessageGenericEnum.PHONE_NULL_OR_EMPTY.getMessage());

    if (validateStringNullOrEmpty(supplierDto.address()) == null)
      throw new ValidationException(MessageGenericEnum.ADDRESS_NULL_OR_EMPTY.getMessage());
  }

  public List<SupplierDto> listAllSuppliers() {
    List<SupplierDto> listVo = this.supplierRepository.listAllSuppliers();

    if (listVo.isEmpty()) throw new ValidationException(MessageGenericEnum.NOT_FOUND.getMessage());

    return listVo;
  }

  public List<SupplierDto> listSuppliersByFilters(SupplierDto supplierDto) {
    List<SupplierDto> listVo = this.supplierRepository.listSuppliersByFilters(
        validateStringNullOrEmpty(supplierDto.name()),
        validateStringNullOrEmpty(supplierDto.address()),
        validateStringNullOrEmpty(supplierDto.email()),
        validateStringNullOrEmpty(supplierDto.phone()),
        supplierDto.status().equals("1")
    );

    if (listVo.isEmpty()) throw new ValidationException(MessageGenericEnum.NOT_FOUND.getMessage());

    return listVo;
  }
}
