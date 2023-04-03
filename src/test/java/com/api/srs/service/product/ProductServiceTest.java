package com.api.srs.service.product;

import com.api.srs.ApplicationConfigTest;
import com.api.srs.repository.product.ProductRepository;
import com.api.srs.vo.product.ProductVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.math.BigInteger;
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
    public void mustReturnAllProducts() {
        List<ProductVo> listProduct = IntStream.range(0, 3).mapToObj(value -> new ProductVo(BigInteger.ONE,"Test", BigDecimal.TEN, 1 )).toList();

        Mockito.when(this.productRepository.listAllProducts()).thenReturn(listProduct);

        this.productService.listAllProducts();

        Mockito.verify(this.productRepository, Mockito.times(1)).listAllProducts();
    }
}
