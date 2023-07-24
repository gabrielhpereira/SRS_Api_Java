package com.api.srs.resource.product;

import com.api.srs.ApplicationConfigTest;
import com.api.srs.dto.product.ProductDto;
import com.api.srs.resource.GenericResourceTest;
import com.api.srs.service.product.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.stream.IntStream;

@DisplayName("ProductResourceTest")
public class ProductResourceTest extends GenericResourceTest implements ApplicationConfigTest {

  private static final String PATH = "/product";
  private static final String MESSAGE = "Message Test";

  @MockBean
  private ProductService productService;

  @Test
  @DisplayName("ListAllProducts return status code 200")
  public void testListAllProducts() throws Exception {
    Mockito.when(this.productService.listAllProducts())
        .thenReturn(IntStream.range(0, 3).mapToObj(value -> new ProductDto(
            BigInteger.ONE, "Test", BigDecimal.TEN, 10)).toList());

    this.genericTestOKStatus(MockMvcRequestBuilders.get(PATH + "/listAllProducts").contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("ListAllProducts return status code 409")
  public void testListAllProductsReturnConflict() throws Exception {
    Mockito.when(this.productService.listAllProducts()).thenThrow(new ValidationException(MESSAGE));

    this.genericTestConflictStatus(MockMvcRequestBuilders.get(PATH + "/listAllProducts").contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  @Test
  @DisplayName("ListAllProducts return status code 500")
  public void testListAllProductReturnInternalError() throws Exception {
    Mockito.when(this.productService.listAllProducts()).thenThrow(new NullPointerException(MESSAGE));

    this.genericTestInternalErrorStatus(MockMvcRequestBuilders.get(PATH + "/listAllProducts").contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  @Test
  @DisplayName("ListProductByFilters return status code 200")
  public void testListProductByFilters() throws Exception {
    Mockito.when(this.productService.listProductByFilters(Mockito.any(ProductDto.class)))
        .thenReturn(IntStream.range(0, 3).mapToObj(value -> new ProductDto(
            BigInteger.ONE, "Test", BigDecimal.TEN, 10)).toList());

    this.genericTestOKStatus(MockMvcRequestBuilders.post(PATH + "/listProductByFilters")
        .content(new ObjectMapper().writeValueAsString(newProductDto()))
        .contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("ListProductByFilters return status code 409")
  public void testListProductByFiltersReturnConflict() throws Exception {
    Mockito.when(this.productService.listProductByFilters(Mockito.any(ProductDto.class)))
        .thenThrow(new ValidationException(MESSAGE));

    this.genericTestConflictStatus(MockMvcRequestBuilders.post(PATH + "/listProductByFilters")
        .content(new ObjectMapper().writeValueAsString(newProductDto()))
        .contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  @Test
  @DisplayName("ListProductByFilters return status code 500")
  public void testListProductByFiltersReturnInternalError() throws Exception {
    Mockito.when(this.productService.listProductByFilters(Mockito.any(ProductDto.class)))
        .thenThrow(new NullPointerException(MESSAGE));

    this.genericTestInternalErrorStatus(MockMvcRequestBuilders.post(PATH + "/listProductByFilters")
        .content(new ObjectMapper().writeValueAsString(newProductDto()))
        .contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  @Test
  @DisplayName("SaveOrUpdateProduct return status code 200")
  public void testSaveOrUpdateProduct() throws Exception {
    this.genericTestOKStatus(MockMvcRequestBuilders.post(PATH + "/saveOrUpdateProduct")
        .content(new ObjectMapper().writeValueAsString(newProductDto()))
        .contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("SaveOrUpdateProduct return status code 409")
  public void testSaveOrUpdateProductReturnConflict() throws Exception {
    Mockito.doThrow(new ValidationException(MESSAGE)).when(this.productService).saveOrUpdateProduct(Mockito.any(ProductDto.class));

    this.genericTestConflictStatus(MockMvcRequestBuilders.post(PATH + "/saveOrUpdateProduct")
        .content(new ObjectMapper().writeValueAsString(newProductDto()))
        .contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  @Test
  @DisplayName("SaveOrUpdateProduct return status code 500")
  public void testSaveOrUpdateReturnInternalError() throws Exception {
    Mockito.doThrow(new NullPointerException(MESSAGE)).when(this.productService).saveOrUpdateProduct(Mockito.any(ProductDto.class));

    this.genericTestInternalErrorStatus(MockMvcRequestBuilders.post(PATH + "/saveOrUpdateProduct")
        .content(new ObjectMapper().writeValueAsString(newProductDto()))
        .contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  @Test
  @DisplayName("DeleteProduct return status code 200")
  public void testDeleteProduct() throws Exception {
    this.genericTestOKStatus(MockMvcRequestBuilders.delete(PATH + "/deleteProduct/1")
        .contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("DeleteProduct return status code 409")
  public void testDeleteProductReturnConflictStatus() throws Exception {
    Mockito.doThrow(new ValidationException(MESSAGE)).when(this.productService).deleteProductById(Mockito.any());

    this.genericTestConflictStatus(MockMvcRequestBuilders.delete(PATH + "/deleteProduct/1")
        .contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  @Test
  @DisplayName("DeleteProduct return status code 500")
  public void testDeleteProductReturnInternalErrorStatus() throws Exception {
    Mockito.doThrow(new NullPointerException(MESSAGE)).when(this.productService).deleteProductById(Mockito.any());

    this.genericTestInternalErrorStatus(MockMvcRequestBuilders.delete(PATH + "/deleteProduct/1")
        .contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  private static ProductDto newProductDto() {
    return new ProductDto(BigInteger.ONE, "Test", BigDecimal.TEN, 10);
  }
}
