package com.api.srs.service.supplier;

import com.api.srs.dto.supplier.SupplierDto;
import com.api.srs.entity.supplier.SupplierEntity;
import com.api.srs.enums.MessageGenericEnum;
import com.api.srs.repository.supplier.SupplierRepository;

import static com.api.srs.shared.Validator.*;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

  private final SupplierRepository supplierRepository;
  private final LogSupplierService logSupplierService;

  public List<SupplierDto> listAllSuppliers() {
    List<SupplierDto> listSupplier = this.supplierRepository.listAllSuppliers();

    if (listSupplier.isEmpty()) throw new ValidationException(MessageGenericEnum.NOT_FOUND.getMessage());

    return listSupplier;
  }

  public List<SupplierDto> listSuppliersByFilters(SupplierDto supplierDto) {
    List<SupplierDto> listSupplier = this.supplierRepository.listSuppliersByFilters(
        validateStringNullOrEmpty(supplierDto.name()),
        validateStringNullOrEmpty(supplierDto.address()),
        validateStringNullOrEmpty(supplierDto.email()),
        validateStringNullOrEmpty(supplierDto.phone()),
        supplierDto.status().equals("1")
    );

    if (listSupplier.isEmpty()) throw new ValidationException(MessageGenericEnum.NOT_FOUND.getMessage());

    return listSupplier;
  }

  @Transactional
  public void enableOrDisableSupplier(Integer id) {
    SupplierEntity supplier = this.supplierRepository.getReferenceById(id);

    supplier.setStatus(!supplier.getStatus());

    this.supplierRepository.save(supplier);
  }

  @Transactional
  public void saveOrUpdateSupplier(SupplierDto supplierDto) {
    validateSupplierDto(supplierDto);

    if (validateIntegerNullOrZero(supplierDto.id()) == null)
      this.saveSupplier(supplierDto);
    else
      this.updateSupplier(supplierDto);
  }

  private void updateSupplier(SupplierDto supplierDto) {
    SupplierEntity supplier = this.supplierRepository.getReferenceById(supplierDto.id());
    SupplierEntity oldSupplier = new SupplierEntity(supplier);

    supplier.setName(supplierDto.name().trim());
    supplier.setEmail(supplierDto.email().trim());
    supplier.setAddress(supplierDto.address().trim());
    supplier.setPhone(supplierDto.phone().trim());
//    supplier.setStatus(supplierDto.status().trim());

    this.supplierRepository.saveAndFlush(supplier);

    this.logSupplierService.saveLogUpdateSupplier(supplier, oldSupplier);
  }

  private void saveSupplier(SupplierDto supplierDto) {
    SupplierEntity supplier =
        SupplierEntity
            .builder()
            .name(supplierDto.name().trim())
            .email(supplierDto.email().trim())
            .phone(supplierDto.phone().trim())
            .address(supplierDto.address().trim())
            .status(true)
            .build();

    this.supplierRepository.saveAndFlush(supplier);

    this.logSupplierService.saveLogNewSupplier(supplier);
  }

  @Transactional
  public void deleteSupplierById(Integer id) {
    try {
      SupplierEntity supplier = this.supplierRepository.getReferenceById(id);

      this.supplierRepository.deleteById(supplier.getId());

      this.logSupplierService.saveLogDeleteSupplier(supplier);
    } catch (EntityNotFoundException e) {
      throw new ValidationException(MessageGenericEnum.NOT_FOUND.getMessage());
    }
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
}
