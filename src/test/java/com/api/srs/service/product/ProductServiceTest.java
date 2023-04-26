package com.api.srs.service.product;

import com.api.srs.ApplicationConfigTest;
import com.api.srs.dto.product.ProductDto;
import com.api.srs.entity.product.ProductEntity;
import com.api.srs.repository.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@DisplayName("ProductServiceTest")
public class ProductServiceTest implements ApplicationConfigTest {

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private LogProductService logProductService;

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("Must return all products")
    public void testReturnAllProducts() {
        List<ProductDto> listProduct = IntStream.range(0, 3).mapToObj(value ->
                new ProductDto(BigInteger.ONE, "Test", BigDecimal.TEN, 1)).toList();

        Mockito.when(this.productRepository.listAllProducts()).thenReturn(listProduct);

        this.productService.listAllProducts();

        Mockito.verify(this.productRepository, Mockito.times(1)).listAllProducts();
    }

    @Test
    @DisplayName("Must return products by filters")
    public void testReturnProductsByFilters() {
        List<ProductDto> listProduct = IntStream.range(0, 3).mapToObj(value ->
                new ProductDto(BigInteger.ONE, "Test", BigDecimal.TEN, 1)).toList();
        Mockito.when(this.productRepository
                .listProductByFilters(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(BigDecimal.class),
                        ArgumentMatchers.anyInt()
                )
        ).thenReturn(listProduct);

        this.productService.listProductByFilters(buildMockDto());

        Mockito.verify(this.productRepository, Mockito.times(1))
                .listProductByFilters(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(BigDecimal.class),
                        ArgumentMatchers.anyInt()
                );
    }

    @Test
    @DisplayName("Must throw exception when return empty list")
    public void testThrowExceptionWhenReturnEmptyList() {
        Mockito.when(this.productRepository
                .listProductByFilters(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(BigDecimal.class),
                        ArgumentMatchers.anyInt()
                )
        ).thenReturn(new ArrayList<>());

        Assertions.assertThrows(ValidationException.class, () ->
                this.productService.listProductByFilters(buildMockDto()), "Product not found!");
    }

    @Test
    @DisplayName("Must save an product")
    public void testSaveAnProduct() {
        ProductDto mock = buildMockDto();
        Mockito.when(mock.id()).thenReturn(null);

        this.productService.saveOrUpdateProduct(mock);

        Mockito.verify(this.productRepository, Mockito.times(1)).saveAndFlush(ArgumentMatchers.any(ProductEntity.class));
        Mockito.verify(this.logProductService, Mockito.times(1)).saveLogNewProduct(ArgumentMatchers.any(ProductEntity.class));
    }

    @Test
    @DisplayName("Must update an product")
    public void testUpdateProduct() {
        ProductDto mock = buildMockDto();

        Mockito.when(this.productRepository.getReferenceById(mock.id()))
                .thenReturn(ProductEntity
                        .builder()
                        .id(BigInteger.ONE)
                        .name("Test")
                        .amount(1)
                        .price(BigDecimal.TEN)
                        .build()
                );

        this.productService.saveOrUpdateProduct(mock);


        Mockito.verify(this.productRepository, Mockito.times(1)).saveAndFlush(ArgumentMatchers.any(ProductEntity.class));
        Mockito.verify(this.logProductService, Mockito.times(1)).saveLogUpdateProduct(ArgumentMatchers.any(ProductEntity.class), ArgumentMatchers.any(ProductEntity.class));
    }

    @Test
    @DisplayName("Must throw Exception when validating name")
    public void testThrowExceptionWhenValidatingName() {
        ProductDto mock = Mockito.mock(ProductDto.class);

        Mockito.when(mock.name()).thenReturn(null);

        Assertions.assertThrows(ValidationException.class, () -> this.productService.saveOrUpdateProduct(mock), "Name is empty or null");

        Mockito.when(mock.name()).thenReturn("");

        Assertions.assertThrows(ValidationException.class, () -> this.productService.saveOrUpdateProduct(mock), "Name is empty or null");
    }

    @Test
    @DisplayName("Must throw Exception when validating amount")
    public void testThrowExceptionWhenValidatingAmount() {
        ProductDto mock = buildMockDto();

        Mockito.when(mock.amount()).thenReturn(null);

        Assertions.assertThrows(ValidationException.class, () -> this.productService.saveOrUpdateProduct(mock), "Amount is null or less than zero");

        Mockito.when(mock.amount()).thenReturn(0);

        Assertions.assertThrows(ValidationException.class, () -> this.productService.saveOrUpdateProduct(mock), "Amount is null or less than zero");
    }

    @Test
    @DisplayName("Must throw Exception when validating price")
    public void testThrowExceptionValidatingPrice() {
        ProductDto mock = buildMockDto();

        Mockito.when(mock.price()).thenReturn(null);

        Assertions.assertThrows(ValidationException.class, () -> this.productService.saveOrUpdateProduct(mock), "Price is null or less than zero");

        Mockito.when(mock.price()).thenReturn(BigDecimal.ZERO);

        Assertions.assertThrows(ValidationException.class, () -> this.productService.saveOrUpdateProduct(mock), "Price is null or less than zero");
    }

    @Test
    @DisplayName("Must delete an product")
    public void testDeleteAnProduct() {
        BigInteger id = BigInteger.ONE;

        ProductEntity mock = Mockito.mock(ProductEntity.class);

        Mockito.when(mock.getId()).thenReturn(id);

        Mockito.when(this.productRepository.getReferenceById(id)).thenReturn(mock);

        this.productService.deleteProductById(id);

        Mockito.verify(this.productRepository, Mockito.times(1)).deleteById(ArgumentMatchers.any());
        Mockito.verify(this.logProductService, Mockito.times(1)).saveLogDeleteProduct(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Must throw Exception product not found")
    public void testDeleteAnProductNotFound() {
        BigInteger id = BigInteger.ONE;

        ProductEntity mock = Mockito.mock(ProductEntity.class);

        Mockito.when(mock.getId()).thenReturn(id);

        Mockito.when(this.productRepository.getReferenceById(id)).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(ValidationException.class, () -> this.productService.deleteProductById(id), "Product not found!");
    }

    private static ProductDto buildMockDto() {
        ProductDto mock = Mockito.mock(ProductDto.class);

        Mockito.when(mock.id()).thenReturn(BigInteger.ONE);
        Mockito.when(mock.name()).thenReturn("test");
        Mockito.when(mock.amount()).thenReturn(1);
        Mockito.when(mock.price()).thenReturn(BigDecimal.ONE);

        return mock;
    }
}
