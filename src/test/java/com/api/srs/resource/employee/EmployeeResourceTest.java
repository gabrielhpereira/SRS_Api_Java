package com.api.srs.resource.employee;

import com.api.srs.ApplicationConfigTest;
import com.api.srs.dto.employee.EmployeeDto;
import com.api.srs.resource.GenericResourceTest;
import com.api.srs.service.employee.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.ValidationException;
import java.util.stream.IntStream;

@DisplayName("EmployeeResourceTest")
public class EmployeeResourceTest extends GenericResourceTest implements ApplicationConfigTest {

    private static final String PATH = "/employee";
    private static final String MESSAGE = "Message Test";

    @MockBean
    private EmployeeService employeeService;

    @Test
    @DisplayName("ListAllEmployee return status code 200")
    public void testListAllEmployee() throws Exception {
        Mockito.when(this.employeeService.listAllEmployees())
                .thenReturn(IntStream.range(0, 3).mapToObj(value -> new EmployeeDto(
                        1, "test", "test", "test", "test", "test", "test")).toList());

        this.genericTestOKStatus(MockMvcRequestBuilders.get(PATH + "/listAllEmployees").contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("ListAllEmployee return status code 409")
    public void testListAllEmployeeReturnConflict() throws Exception {
        Mockito.when(this.employeeService.listAllEmployees()).thenThrow(new ValidationException(MESSAGE));

        this.genericTestConflictStatus(MockMvcRequestBuilders.get(PATH + "/listAllEmployees").contentType(MediaType.APPLICATION_JSON), MESSAGE);
    }

    @Test
    @DisplayName("ListAllEmployee return status code 500")
    public void testListAllEmployeeReturnInternalError() throws Exception {
        Mockito.when(this.employeeService.listAllEmployees()).thenThrow(new NullPointerException(MESSAGE));

        this.genericTestInternalErrorStatus(MockMvcRequestBuilders.get(PATH + "/listAllEmployees").contentType(MediaType.APPLICATION_JSON), MESSAGE);
    }

    @Test
    @DisplayName("ListEmployeeByFilters return status code 200")
    public void testListEmployeeByFilters() throws Exception {
        Mockito.when(this.employeeService.listEmployeeByFilters(Mockito.any(EmployeeDto.class)))
                .thenReturn(IntStream.range(0, 3).mapToObj(value -> new EmployeeDto(
                        1, "test", "test", "test", "test", "test", "test")).toList());

        this.genericTestOKStatus(MockMvcRequestBuilders.post(PATH + "/listEmployeeByFilters")
                .content(new ObjectMapper().writeValueAsString(newEmployeeDto()))
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("ListEmployeeByFilters return status code 409")
    public void testListEmployeeByFiltersReturnConflict() throws Exception {
        Mockito.when(this.employeeService.listEmployeeByFilters(Mockito.any(EmployeeDto.class)))
                .thenThrow(new ValidationException(MESSAGE));

        this.genericTestConflictStatus(MockMvcRequestBuilders.post(PATH + "/listEmployeeByFilters")
                .content(new ObjectMapper().writeValueAsString(newEmployeeDto()))
                .contentType(MediaType.APPLICATION_JSON), MESSAGE);
    }

    @Test
    @DisplayName("ListEmployeeByFilters return status code 500")
    public void testListEmployeeByFiltersReturnInternalError() throws Exception {
        Mockito.when(this.employeeService.listEmployeeByFilters(Mockito.any(EmployeeDto.class)))
                .thenThrow(new NullPointerException(MESSAGE));

        this.genericTestInternalErrorStatus(MockMvcRequestBuilders.post(PATH + "/listEmployeeByFilters")
                .content(new ObjectMapper().writeValueAsString(newEmployeeDto()))
                .contentType(MediaType.APPLICATION_JSON), MESSAGE);
    }

    @Test
    @DisplayName("SaveOrUpdateEmployee return status code 200")
    public void testSaveOrUpdateEmployee() throws Exception {
        this.genericTestOKStatus(MockMvcRequestBuilders.post(PATH + "/saveOrUpdateEmployee")
                .content(new ObjectMapper().writeValueAsString(newEmployeeDto()))
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("SaveOrUpdateEmployee return status code 409")
    public void testSaveOrUpdateEmployeeReturnConflictStatus() throws Exception {
        Mockito.doThrow(new ValidationException(MESSAGE)).when(this.employeeService).saveOrUpdateEmployee(Mockito.any(EmployeeDto.class));

        this.genericTestConflictStatus(MockMvcRequestBuilders.post(PATH + "/saveOrUpdateEmployee")
                .content(new ObjectMapper().writeValueAsString(newEmployeeDto()))
                .contentType(MediaType.APPLICATION_JSON), MESSAGE);
    }

    @Test
    @DisplayName("SaveOrUpdateEmployee return status code 500")
    public void testSaveOrUpdateEmployeeReturnInternalErrorStatus() throws Exception {
        Mockito.doThrow(new NullPointerException(MESSAGE)).when(this.employeeService).saveOrUpdateEmployee(Mockito.any(EmployeeDto.class));

        this.genericTestInternalErrorStatus(MockMvcRequestBuilders.post(PATH + "/saveOrUpdateEmployee")
                .content(new ObjectMapper().writeValueAsString(newEmployeeDto()))
                .contentType(MediaType.APPLICATION_JSON), MESSAGE);
    }

    @Test
    @DisplayName("DeleteEmployee return status code 200")
    public void testDeleteEmployee() throws Exception {
        this.genericTestOKStatus(MockMvcRequestBuilders.delete(PATH + "/deleteEmployee/1")
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("DeleteEmployee return status code 409")
    public void testDeleteEmployeeReturnConflictStatus() throws Exception {
        Mockito.doThrow(new ValidationException(MESSAGE)).when(this.employeeService).deleteEmployeeById(Mockito.anyInt());

        this.genericTestConflictStatus(MockMvcRequestBuilders.delete(PATH + "/deleteEmployee/1")
                .contentType(MediaType.APPLICATION_JSON), MESSAGE);
    }

    @Test
    @DisplayName("DeleteEmployee return status code 500")
    public void testDeleteEmployeeReturnInternalErrorStatus() throws Exception {
        Mockito.doThrow(new NullPointerException(MESSAGE)).when(this.employeeService).deleteEmployeeById(Mockito.anyInt());

        this.genericTestInternalErrorStatus(MockMvcRequestBuilders.delete(PATH + "/deleteEmployee/1")
                .contentType(MediaType.APPLICATION_JSON), MESSAGE);
    }

    private static EmployeeDto newEmployeeDto() {
        return new EmployeeDto(1, "test", "test", "test", "test", "test", "test");
    }
}