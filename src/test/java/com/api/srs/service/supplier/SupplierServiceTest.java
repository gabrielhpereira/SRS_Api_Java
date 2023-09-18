package com.api.srs.service.supplier;

import com.api.srs.ApplicationConfigTest;
import com.api.srs.dto.supplier.SupplierDto;
import com.api.srs.entity.supplier.SupplierEntity;
import com.api.srs.enums.MessageGenericEnum;
import com.api.srs.repository.supplier.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@DisplayName("SupplierServiceTest")
public class SupplierServiceTest implements ApplicationConfigTest {

  @MockBean
  private SupplierRepository supplierRepository;

  @MockBean
  private LogSupplierService logSupplierService;

  @Autowired
  private SupplierService supplierService;

  @Test
  @DisplayName("Must return all suppliers")
  public void testReturnAllSuppliers() {
    List<SupplierDto> listSupplier = IntStream.range(0, 3).mapToObj(value ->
        new SupplierDto(
            1,
            "Test",
            "test address",
            "test@test.com",
            "11123456789",
            "1")
    ).toList();

    Mockito.when(this.supplierRepository.listAllSuppliers()).thenReturn(listSupplier);

    this.supplierService.listAllSuppliers();

    Mockito.verify(this.supplierRepository, Mockito.times(1)).listAllSuppliers();
  }

  @Test
  @DisplayName("Must return list all active suppliers")
  public void testReturnListAllActiveSuppliers() {
    List<SupplierDto> listSupplier = IntStream.range(0, 3).mapToObj(value ->
        new SupplierDto(
            1,
            "test",
            "test address",
            "test@test.com",
            "11123456789",
            "1"
        )
    ).toList();

    Mockito.when(this.supplierRepository.listAllActiveSuppliers()).thenReturn(listSupplier);

    this.supplierService.listAllActiveSuppliers();

    Mockito.verify(this.supplierRepository, Mockito.times(1)).listAllActiveSuppliers();
  }

  @Test
  @DisplayName("Must return suppliers by filters")
  public void testReturnSuppliersByFilters() {
    List<SupplierDto> listSupplier = IntStream.range(0, 3).mapToObj(value ->
        new SupplierDto(
            1,
            "test",
            "test address",
            "test@test.com",
            "11123456789",
            "1")
    ).toList();

    Mockito.when(this.supplierRepository
        .listSuppliersByFilters(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyBoolean()
        )
    ).thenReturn(listSupplier);

    this.supplierService.listSuppliersByFilters(buildMockDto());

    Mockito.verify(this.supplierRepository, Mockito.times(1))
        .listSuppliersByFilters(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyBoolean()
        );
  }

  @Test
  @DisplayName("Must throw exception when return empty list")
  public void testThrowExceptionWhenReturnEmptyList() {
    Mockito.when(this.supplierRepository
        .listSuppliersByFilters(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyBoolean()
        )
    ).thenReturn(new ArrayList<>());

    Assertions.assertThrows(ValidationException.class, () ->
        this.supplierService.listSuppliersByFilters(buildMockDto()), MessageGenericEnum.NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("Must enable or disable supplier")
  public void testEnableOrDisableSupplier() {
    Integer id = 1;
    SupplierEntity mock = Mockito.mock(SupplierEntity.class);

    Mockito.when(mock.getId()).thenReturn(id);
    Mockito.when(this.supplierRepository.getReferenceById(id)).thenReturn(mock);
    Mockito.when(mock.getStatus()).thenReturn(ArgumentMatchers.anyBoolean());

    this.supplierService.enableOrDisableSupplier(id);

    Mockito.verify(this.supplierRepository, Mockito.times(1)).saveAndFlush(ArgumentMatchers.any(SupplierEntity.class));
    Mockito.verify(this.logSupplierService, Mockito.times(1))
        .saveLogUpdateSupplier(ArgumentMatchers.any(SupplierEntity.class), ArgumentMatchers.any(SupplierEntity.class));
  }

  @Test
  @DisplayName("Must save an supplier")
  public void testSaveAnSupplier() {
    SupplierDto mock = buildMockDto();
    Mockito.when(mock.id()).thenReturn(null);

    this.supplierService.saveOrUpdateSupplier(mock);

    Mockito.verify(this.supplierRepository, Mockito.times(1)).saveAndFlush(ArgumentMatchers.any(SupplierEntity.class));
    Mockito.verify(this.logSupplierService, Mockito.times(1)).saveLogNewSupplier(ArgumentMatchers.any(SupplierEntity.class));
  }

  @Test
  @DisplayName("Must update an supplier")
  public void testUpdateSupplier() {
    SupplierDto mock = buildMockDto();

    Mockito.when(this.supplierRepository.getReferenceById(mock.id()))
        .thenReturn(SupplierEntity
            .builder()
            .id(1)
            .name("test")
            .address("test address")
            .email("test@test.com")
            .phone("11123456789")
            .status(true)
            .build()
        );

    this.supplierService.saveOrUpdateSupplier(mock);

    Mockito.verify(this.supplierRepository, Mockito.times(1)).saveAndFlush(ArgumentMatchers.any(SupplierEntity.class));
    Mockito.verify(this.logSupplierService, Mockito.times(1))
        .saveLogUpdateSupplier(ArgumentMatchers.any(SupplierEntity.class), ArgumentMatchers.any(SupplierEntity.class));
  }

  @Test
  @DisplayName("Must throw exception when validating name")
  public void testThrowExceptionWhenValidationName() {
    SupplierDto mock = buildMockDto();

    Mockito.when(mock.name()).thenReturn(null);
    Assertions.assertThrows(ValidationException.class, () -> this.supplierService.saveOrUpdateSupplier(mock));

    Mockito.when(mock.name()).thenReturn("");
    Assertions.assertThrows(ValidationException.class, () -> this.supplierService.saveOrUpdateSupplier(mock));
  }

  @Test
  @DisplayName("Must throw exception when validating address")
  public void testThrowExceptionWhenValidationAddress() {
    SupplierDto mock = buildMockDto();

    Mockito.when(mock.address()).thenReturn(null);
    Assertions.assertThrows(ValidationException.class, () -> this.supplierService.saveOrUpdateSupplier(mock));

    Mockito.when(mock.address()).thenReturn("");
    Assertions.assertThrows(ValidationException.class, () -> this.supplierService.saveOrUpdateSupplier(mock));
  }

  @Test
  @DisplayName("Must throw exception when validating email")
  public void testThrowExceptionWhenValidationEmail() {
    SupplierDto mock = buildMockDto();

    Mockito.when(mock.email()).thenReturn(null);
    Assertions.assertThrows(ValidationException.class, () -> this.supplierService.saveOrUpdateSupplier(mock));

    Mockito.when(mock.email()).thenReturn("");
    Assertions.assertThrows(ValidationException.class, () -> this.supplierService.saveOrUpdateSupplier(mock));
  }

  @Test
  @DisplayName("Must throw exception when validating phone")
  public void testThrowExceptionWhenValidationPhone() {
    SupplierDto mock = buildMockDto();

    Mockito.when(mock.phone()).thenReturn(null);
    Assertions.assertThrows(ValidationException.class, () -> this.supplierService.saveOrUpdateSupplier(mock));

    Mockito.when(mock.phone()).thenReturn(null);
    Assertions.assertThrows(ValidationException.class, () -> this.supplierService.saveOrUpdateSupplier(mock));
  }

  @Test
  @DisplayName("Must delete an supplier")
  public void testDeleteAnSupplier() {
    Integer id = 1;

    SupplierEntity mock = Mockito.mock(SupplierEntity.class);

    Mockito.when(mock.getId()).thenReturn(id);
    Mockito.when(this.supplierRepository.getReferenceById(id)).thenReturn(mock);

    this.supplierService.deleteSupplierById(id);

    Mockito.verify(this.supplierRepository, Mockito.times(1)).deleteById(ArgumentMatchers.anyInt());
    Mockito.verify(this.logSupplierService, Mockito.times(1)).saveLogDeleteSupplier(ArgumentMatchers.any(SupplierEntity.class));
  }

  @Test
  @DisplayName("Must throw exception supplier not found")
  public void testDeleteAnSupplierNotFound() {
    Integer id = 1;

    SupplierEntity mock = Mockito.mock(SupplierEntity.class);

    Mockito.when(mock.getId()).thenReturn(id);
    Mockito.when(this.supplierRepository.getReferenceById(id)).thenThrow(EntityNotFoundException.class);

    Assertions.assertThrows(ValidationException.class, () -> this.supplierService.deleteSupplierById(id));
  }

  private static SupplierDto buildMockDto() {
    SupplierDto mock = Mockito.mock(SupplierDto.class);

    Mockito.when(mock.id()).thenReturn(1);
    Mockito.when(mock.name()).thenReturn("test");
    Mockito.when(mock.address()).thenReturn("test address");
    Mockito.when(mock.email()).thenReturn("test@test.com");
    Mockito.when(mock.phone()).thenReturn("11123456789");
    Mockito.when(mock.status()).thenReturn("1");

    return mock;
  }
}
