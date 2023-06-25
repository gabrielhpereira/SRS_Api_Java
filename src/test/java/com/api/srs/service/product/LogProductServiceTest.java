package com.api.srs.service.product;

import com.api.srs.ApplicationConfigTest;
import com.api.srs.entity.product.LogProductEntity;
import com.api.srs.entity.product.ProductEntity;
import com.api.srs.repository.product.LogProductRepository;
import com.api.srs.dto.product.LogProductDto;
import com.api.srs.shared.DateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.stream.IntStream;

@DisplayName("LogProductServiceTest")
public class LogProductServiceTest implements ApplicationConfigTest {

  @MockBean
  private LogProductRepository logProductRepository;

  @Autowired
  private LogProductService logProductService;

  @Test
  @DisplayName("Must return all product logs")
  public void testReturnAllLogProduct() {
    BigInteger idProduct = BigInteger.ONE;

    Mockito.when(this.logProductRepository.listAllLogProduct(idProduct)).thenReturn(
        IntStream.range(0, 3).mapToObj(value -> new LogProductDto(1, idProduct, "Test description", DateTime.nowDate())).toList());

    this.logProductService.listAllLogProduct(idProduct);

    Mockito.verify(this.logProductRepository, Mockito.times(1)).listAllLogProduct(idProduct);
  }

  @Test
  @DisplayName("Must save log new product")
  public void testSaveLogNewProduct() {
    this.logProductService.saveLogNewProduct(Mockito.mock(ProductEntity.class));

    Mockito.verify(this.logProductRepository, Mockito.times(1)).save(ArgumentMatchers.any(LogProductEntity.class));
  }

  @Test
  @DisplayName("Must save log update product")
  public void testSaveLogUpdateProduct() {
    ProductEntity newProductEntity = Mockito.mock(ProductEntity.class);
    Mockito.when(newProductEntity.getName()).thenReturn("Test new");
    Mockito.when(newProductEntity.getAmount()).thenReturn(1);
    Mockito.when(newProductEntity.getPrice()).thenReturn(BigDecimal.ONE);

    ProductEntity oldProductEntity = Mockito.mock(ProductEntity.class);
    Mockito.when(oldProductEntity.getName()).thenReturn("Test old");
    Mockito.when(oldProductEntity.getAmount()).thenReturn(2);
    Mockito.when(oldProductEntity.getPrice()).thenReturn(BigDecimal.TEN);

    this.logProductService.saveLogUpdateProduct(newProductEntity, oldProductEntity);

    Mockito.verify(this.logProductRepository, Mockito.times(1)).save(ArgumentMatchers.any(LogProductEntity.class));
  }

  @Test
  @DisplayName("Update has not changed")
  public void testUpdateHasNotChanged() {
    ProductEntity newProductEntity = Mockito.mock(ProductEntity.class);
    Mockito.when(newProductEntity.getName()).thenReturn("Test new");
    Mockito.when(newProductEntity.getAmount()).thenReturn(1);
    Mockito.when(newProductEntity.getPrice()).thenReturn(BigDecimal.ONE);

    this.logProductService.saveLogUpdateProduct(newProductEntity, newProductEntity);

    Mockito.verify(this.logProductRepository, Mockito.never()).save(ArgumentMatchers.any(LogProductEntity.class));
  }

  @Test
  @DisplayName("Must save log delete product")
  public void testSaveLogDeleteProduct() {
    this.logProductService.saveLogDeleteProduct(Mockito.mock(ProductEntity.class));

    Mockito.verify(this.logProductRepository, Mockito.times(1)).save(ArgumentMatchers.any(LogProductEntity.class));
  }
}