package com.api.srs.service.product;

import com.api.srs.ApplicationConfigTest;
import com.api.srs.dto.product.ProductDto;
import com.api.srs.repository.product.ProductRepository;
import com.api.srs.vo.product.ProductVo;
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

public class ProductServiceTest extends ApplicationConfigTest {

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private LogProductService logProductService;

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("Must return all products")
    public void testReturnAllProducts() {
        List<ProductVo> listProduct = IntStream.range(0, 3).mapToObj(value ->
                new ProductVo(BigInteger.ONE, "Test", BigDecimal.TEN, 1)).toList();

        Mockito.when(this.productRepository.listAllProducts()).thenReturn(listProduct);

        this.productService.listAllProducts();

        Mockito.verify(this.productRepository, Mockito.times(1)).listAllProducts();
    }

    @Test
    @DisplayName("Must return products by filters")
    public void testReturnProductsByFilters() {
        List<ProductVo> listProduct = IntStream.range(0, 3).mapToObj(value ->
                new ProductVo(BigInteger.ONE, "Test", BigDecimal.TEN, 1)).toList();
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

    private static ProductDto buildMockDto() {
        ProductDto mock = Mockito.mock(ProductDto.class);

        Mockito.when(mock.getId()).thenReturn(BigInteger.ONE);
        Mockito.when(mock.getName()).thenReturn("test");
        Mockito.when(mock.getAmount()).thenReturn(1);
        Mockito.when(mock.getPrice()).thenReturn(BigDecimal.ONE);

        return mock;
    }
}
