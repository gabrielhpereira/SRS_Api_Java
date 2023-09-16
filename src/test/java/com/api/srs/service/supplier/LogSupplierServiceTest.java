package com.api.srs.service.supplier;

import com.api.srs.ApplicationConfigTest;
import com.api.srs.dto.supplier.LogSupplierDto;
import com.api.srs.entity.supplier.LogSupplierEntity;
import com.api.srs.entity.supplier.SupplierEntity;
import com.api.srs.repository.supplier.LogSupplierRepository;
import com.api.srs.shared.DateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.IntStream;

@DisplayName("LogSupplierServiceTest")
public class LogSupplierServiceTest implements ApplicationConfigTest {

  @MockBean
  private LogSupplierRepository logSupplierRepository;

  @Autowired
  private LogSupplierService logSupplierService;

  @Test
  @DisplayName("Must return all supplier logs")
  public void testReturnAllLogSupplier() {
    Integer idSupplier = 1;

    Mockito.when(this.logSupplierRepository.listAllLogSupplier(idSupplier)).thenReturn(
        IntStream.range(0, 3).mapToObj(value -> new LogSupplierDto(1, idSupplier, "Test description", DateTime.nowDate())).toList());

    this.logSupplierService.listAllLogSupplier(idSupplier);

    Mockito.verify(this.logSupplierRepository, Mockito.times(1)).listAllLogSupplier(ArgumentMatchers.anyInt());
  }

  @Test
  @DisplayName("Must save log new supplier")
  public void testSaveLogNewSupplier() {
    this.logSupplierService.saveLogNewSupplier(Mockito.mock(SupplierEntity.class));
    Mockito.verify(this.logSupplierRepository, Mockito.times(1)).save(ArgumentMatchers.any(LogSupplierEntity.class));
  }

  @Test
  @DisplayName("Must save log update supplier")
  public void testSaveUpdateSupplier() {
    SupplierEntity newSupplierEntity = Mockito.mock(SupplierEntity.class);
    Mockito.when(newSupplierEntity.getName()).thenReturn("new name");
    Mockito.when(newSupplierEntity.getEmail()).thenReturn("newtest@test.com");
    Mockito.when(newSupplierEntity.getAddress()).thenReturn("new address");
    Mockito.when(newSupplierEntity.getPhone()).thenReturn("11123456789");
    Mockito.when(newSupplierEntity.getStatus()).thenReturn(true);

    SupplierEntity oldSupplierEntity = Mockito.mock(SupplierEntity.class);
    Mockito.when(oldSupplierEntity.getName()).thenReturn("old name");
    Mockito.when(oldSupplierEntity.getEmail()).thenReturn("oldtest@test.com");
    Mockito.when(oldSupplierEntity.getAddress()).thenReturn("old address");
    Mockito.when(oldSupplierEntity.getPhone()).thenReturn("11987654321");
    Mockito.when(oldSupplierEntity.getStatus()).thenReturn(false);

    this.logSupplierService.saveLogUpdateSupplier(newSupplierEntity, oldSupplierEntity);

    Mockito.verify(this.logSupplierRepository, Mockito.times(1)).save(ArgumentMatchers.any(LogSupplierEntity.class));
  }

  @Test
  @DisplayName("Update has not changed")
  public void testUpdateHasNotChanged() {
    SupplierEntity newSupplierEntity = Mockito.mock(SupplierEntity.class);
    Mockito.when(newSupplierEntity.getName()).thenReturn("new name");
    Mockito.when(newSupplierEntity.getEmail()).thenReturn("newtest@test.com");
    Mockito.when(newSupplierEntity.getAddress()).thenReturn("new address");
    Mockito.when(newSupplierEntity.getPhone()).thenReturn("11123456789");
    Mockito.when(newSupplierEntity.getStatus()).thenReturn(true);

    this.logSupplierService.saveLogUpdateSupplier(newSupplierEntity, newSupplierEntity);

    Mockito.verify(this.logSupplierRepository, Mockito.never()).save(ArgumentMatchers.any(LogSupplierEntity.class));
  }

  @Test
  @DisplayName("Must save log delete supplier")
  public void testSaveLogDeleteSupplier() {
    this.logSupplierService.saveLogDeleteSupplier(Mockito.mock(SupplierEntity.class));

    Mockito.verify(this.logSupplierRepository, Mockito.times(1)).save(ArgumentMatchers.any(LogSupplierEntity.class));
  }
}
