package com.api.srs.resource.supplier;

import com.api.srs.ApplicationConfigTest;
import com.api.srs.dto.supplier.SupplierDto;
import com.api.srs.resource.GenericResourceTest;
import com.api.srs.service.supplier.SupplierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.ValidationException;
import java.util.stream.IntStream;

@DisplayName("SupplierResourceTest")
public class SupplierResourceTest extends GenericResourceTest implements ApplicationConfigTest {
  private static final String PATH = "/supplier";

  @MockBean
  private SupplierService supplierService;

  @Test
  @DisplayName("ListAllSuppliers return status code 200")
  public void testListAllSuppliers() throws Exception {
    Mockito.when(this.supplierService.listAllSuppliers())
        .thenReturn(IntStream.range(0, 3).mapToObj(value ->
            new SupplierDto(
                1,
                "test",
                "test address",
                "test@test.com",
                "11123456789",
                true
            )
        ).toList());

    this.genericTestOKStatus(MockMvcRequestBuilders.get(PATH + "/listAllSuppliers").contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("ListAllSuppliers return status code 409")
  public void testListAllSuppliersReturnConflict() throws Exception {
    Mockito.when(this.supplierService.listAllSuppliers()).thenThrow(new ValidationException(MESSAGE));
    this.genericTestConflictStatus(MockMvcRequestBuilders.get(PATH + "/listAllSuppliers").contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  @Test
  @DisplayName("ListAllSuppliers return status code 500")
  public void testListAllSuppliersReturnError() throws Exception {
    Mockito.when(this.supplierService.listAllSuppliers()).thenThrow(new NullPointerException(MESSAGE));
    this.genericTestInternalErrorStatus(MockMvcRequestBuilders.get(PATH + "/listAllSuppliers").contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  @Test
  @DisplayName("ListAllActiveSuppliers return status code 200")
  public void testListAllActiveSuppliers() throws Exception {
    Mockito.when(this.supplierService.listAllSuppliers())
        .thenReturn(IntStream.range(0, 3).mapToObj(value -> new SupplierDto(
                1,
                "test",
                "test address",
                "test@test.com",
                "11123456789",
                true
            )
        ).toList());

    this.genericTestOKStatus(MockMvcRequestBuilders.get(PATH + "/listAllActiveSuppliers").contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("ListAllActiveSuppliers return status code 409")
  public void testListAllActiveSuppliersReturnConflict() throws Exception {
    Mockito.when(this.supplierService.listAllActiveSuppliers()).thenThrow(new ValidationException(MESSAGE));
    this.genericTestConflictStatus(MockMvcRequestBuilders.get(PATH + "/listAllActiveSuppliers").contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  @Test
  @DisplayName("ListAllActiveSuppliers return status code 500")
  public void testListAllActiveSuppliersReturnInternalError() throws Exception {
    Mockito.when(this.supplierService.listAllActiveSuppliers()).thenThrow(new NullPointerException(MESSAGE));
    this.genericTestInternalErrorStatus(MockMvcRequestBuilders.get(PATH + "/listAllActiveSuppliers").contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  @Test
  @DisplayName("ListSupplierByFilters return status code 200")
  public void testListSupplierByFilters() throws Exception {
    Mockito.when(this.supplierService.listSuppliersByFilters(Mockito.any(SupplierDto.class)))
        .thenReturn(IntStream.range(0, 3).mapToObj(value -> new SupplierDto(
                1,
                "test",
                "test address",
                "test@test.com",
                "11123456789",
                true
            )
        ).toList());

    this.genericTestOKStatus(MockMvcRequestBuilders.post(PATH + "/listSupplierByFilters")
        .content(new ObjectMapper().writeValueAsString(newSupplierDto()))
        .contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("ListSupplierByFilters return status code 409")
  public void testListSupplierByFiltersReturnConflict() throws Exception {
    Mockito.when(this.supplierService.listSuppliersByFilters(Mockito.any(SupplierDto.class)))
        .thenThrow(new ValidationException(MESSAGE));

    this.genericTestConflictStatus(MockMvcRequestBuilders.post(PATH + "/listSupplierByFilters")
        .content(new ObjectMapper().writeValueAsString(newSupplierDto()))
        .contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  @Test
  @DisplayName("ListSupplierByFilters return status code 500")
  public void testListSupplierByFiltersReturnInternalError() throws Exception {
    Mockito.when(this.supplierService.listSuppliersByFilters(Mockito.any(SupplierDto.class)))
        .thenThrow(new NullPointerException(MESSAGE));

    this.genericTestInternalErrorStatus(MockMvcRequestBuilders.post(PATH + "/listSupplierByFilters")
        .content(new ObjectMapper().writeValueAsString(newSupplierDto()))
        .contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  @Test
  @DisplayName("/EnableOrDisableSupplier return status code 200")
  public void testEnableOrDisableSupplier() throws Exception {
    this.genericTestOKStatus(MockMvcRequestBuilders.put(PATH + "/enableOrDisableSupplier/1")
        .contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("EnableOrDisableSupplier return status code 409")
  public void testEnableOrDisableSupplierReturnConflictStatus() throws Exception {
    Mockito.doThrow(new ValidationException(MESSAGE)).when(this.supplierService).enableOrDisableSupplier(Mockito.anyInt());

    this.genericTestConflictStatus(MockMvcRequestBuilders.put(PATH + "/enableOrDisableSupplier/1")
        .contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  @Test
  @DisplayName("EnableOrDisableSupplier return status code 500")
  public void testEnableOrDisableSupplierReturnInternalError() throws Exception {
    Mockito.doThrow(new NullPointerException(MESSAGE)).when(this.supplierService).enableOrDisableSupplier(Mockito.anyInt());

    this.genericTestInternalErrorStatus(MockMvcRequestBuilders.put(PATH + "/enableOrDisableSupplier/1")
        .contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  @Test
  @DisplayName("SaveOrUpdate return status code 200")
  public void testSaveOrUpdateSupplier() throws Exception {
    this.genericTestOKStatus(MockMvcRequestBuilders.post(PATH + "/saveOrUpdateSupplier")
        .content(new ObjectMapper().writeValueAsString(newSupplierDto()))
        .contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("SaveOrUpdate return status code 409")
  public void testSaveOrUpdateSupplierReturnConflict() throws Exception {
    Mockito.doThrow(new ValidationException(MESSAGE)).when(this.supplierService).saveOrUpdateSupplier(Mockito.any(SupplierDto.class));

    this.genericTestConflictStatus(MockMvcRequestBuilders.post(PATH + "/saveOrUpdateSupplier")
        .content(new ObjectMapper().writeValueAsString(newSupplierDto()))
        .contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  @Test
  @DisplayName("SaveOrUpdate return status code 500")
  public void testSaveOrUpdateSupplierReturnInternalError() throws Exception {
    Mockito.doThrow(new NullPointerException(MESSAGE)).when(this.supplierService).saveOrUpdateSupplier(Mockito.any(SupplierDto.class));

    this.genericTestInternalErrorStatus(MockMvcRequestBuilders.post(PATH + "/saveOrUpdateSupplier")
        .content(new ObjectMapper().writeValueAsString(newSupplierDto()))
        .contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  @Test
  @DisplayName("DeleteSupplier return status code 200")
  public void testDeleteSupplier() throws Exception {
    this.genericTestOKStatus(MockMvcRequestBuilders.delete(PATH + "/deleteSupplier/1")
        .contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("DeleteSupplier return status code 409")
  public void testDeleteSupplierReturnConflictStatus() throws Exception {
    Mockito.doThrow(new ValidationException(MESSAGE)).when(this.supplierService).deleteSupplierById(Mockito.anyInt());

    this.genericTestConflictStatus(MockMvcRequestBuilders.delete(PATH + "/deleteSupplier/1")
        .contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }

  @Test
  @DisplayName("DeleteSupplier return status code 500")
  public void testDeleteSupplierReturnInternalError() throws Exception {
    Mockito.doThrow(new NullPointerException(MESSAGE)).when(this.supplierService).deleteSupplierById(Mockito.anyInt());

    this.genericTestInternalErrorStatus(MockMvcRequestBuilders.delete(PATH + "/deleteSupplier/1")
        .contentType(MediaType.APPLICATION_JSON), MESSAGE);
  }


  private static SupplierDto newSupplierDto() {
    return new SupplierDto(
        1,
        "test",
        "test address",
        "test@test.com",
        "11123456789",
        true
    );
  }
}