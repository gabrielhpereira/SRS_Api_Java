package com.api.srs.resource.employee;

import com.api.srs.ApplicationConfigTest;
import com.api.srs.dto.employee.EmployeeDto;
import com.api.srs.service.employee.EmployeeService;
import com.api.srs.vo.employee.EmployeeVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.validation.ValidationException;
import java.util.stream.IntStream;

@DisplayName("EmployeeResourceTest")
public class EmployeeResourceTest extends ApplicationConfigTest {

    private static final String PATH = "/employee";

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("ListAllEmployee return status code 200")
    public void testListAllEmployee() throws Exception {
        Mockito.when(this.employeeService.listAllEmployee()).thenReturn(
                IntStream.range(0, 3).mapToObj(value -> new EmployeeVo(
                        1, "test", "test", "test", "test", "test", "test")).toList());

        this.genericTestOKStatus(MockMvcRequestBuilders.get(
                PATH + "/listAllEmployee").contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("ListAllEmployee return status code 409")
    public void testListAllEmployeeReturnConflict() throws Exception {
        String message = "Employee not found!";
        Mockito.when(this.employeeService.listAllEmployee())
                .thenThrow(new ValidationException(message));

        this.genericTestConflictStatus(MockMvcRequestBuilders.get(
                PATH + "/listAllEmployee").contentType(MediaType.APPLICATION_JSON), message);
    }

    @Test
    @DisplayName("ListAllEmployee return status code 500")
    public void testListAllEmployeeReturnInternalError() throws Exception {
        String message = "null";
        Mockito.when(this.employeeService.listAllEmployee())
                .thenThrow(new NullPointerException(message));

        this.genericTestInternalErrorStatus(MockMvcRequestBuilders.get(
                PATH + "/listAllEmployee").contentType(MediaType.APPLICATION_JSON), message);
    }

    @Test
    @DisplayName("ListEmployeeByFilters return status code 200")
    public void testListEmployeeByFilters() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(1);

        Mockito.when(this.employeeService.listEmployeeByFilters(employeeDto)).thenReturn(
                IntStream.range(0, 3).mapToObj(value -> new EmployeeVo(
                        1, "test", "test", "test", "test", "test", "test")).toList());

        this.genericTestOKStatus(MockMvcRequestBuilders.post(PATH + "/listEmployeeByFilters")
                .content(new ObjectMapper().writeValueAsString(employeeDto))
                .contentType(MediaType.APPLICATION_JSON)
        );
    }

    @Test
    @DisplayName("ListEmployeeByFilters return status code 409")
    public void testListEmployeeByFiltersReturnConflict() throws Exception {
        String message = "Employee not found!";

        Mockito.when(this.employeeService.listEmployeeByFilters(Mockito.any(EmployeeDto.class)))
                .thenThrow(new ValidationException(message));

        this.genericTestConflictStatus(MockMvcRequestBuilders.post(PATH + "/listEmployeeByFilters")
                .content(new ObjectMapper().writeValueAsString(new EmployeeDto()))
                .contentType(MediaType.APPLICATION_JSON), message);
    }

    @Test
    @DisplayName("ListEmployeeByFilters return status code 500")
    public void testListEmployeeByFiltersReturnInternalError() throws Exception {
        String message = "null";

        EmployeeDto employeeDto = new EmployeeDto();

        Mockito.when(this.employeeService.listEmployeeByFilters(Mockito.any(EmployeeDto.class)))
                .thenThrow(new NullPointerException(message));

        this.genericTestInternalErrorStatus(MockMvcRequestBuilders.post(PATH + "/listEmployeeByFilters")
                .content(new ObjectMapper().writeValueAsString(employeeDto))
                .contentType(MediaType.APPLICATION_JSON), message);
    }

    private void genericTestOKStatus(RequestBuilder requestBuilder) throws Exception {
        MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private void genericTestConflictStatus(RequestBuilder requestBuilder, String message) throws Exception {
        MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andReturn()
                .getResponse();

        Assertions.assertTrue(HttpStatus.CONFLICT.value() == response.getStatus()
                && response.getContentAsString().equalsIgnoreCase(message));
    }

    private void genericTestInternalErrorStatus(RequestBuilder requestBuilder, String message) throws Exception {
        MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn()
                .getResponse();

        Assertions.assertTrue(HttpStatus.INTERNAL_SERVER_ERROR.value() == response.getStatus()
                && response.getContentAsString().equalsIgnoreCase(message));
    }
}
