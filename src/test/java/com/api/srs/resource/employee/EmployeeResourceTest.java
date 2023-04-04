package com.api.srs.resource.employee;

import com.api.srs.ApplicationConfigTest;
import com.api.srs.service.employee.EmployeeService;
import com.api.srs.vo.employee.EmployeeVo;
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
    @DisplayName("listAllEmployee return status code 200")
    public void testListAllEmployee() throws Exception {
        Mockito.when(this.employeeService.listAllEmployee()).thenReturn(
                IntStream.range(0, 3).mapToObj(value -> new EmployeeVo(
                        1, "test", "test", "test", "test", "test", "test")).toList());

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/listAllEmployee")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @DisplayName("listAllEmployee return status code 409")
    public void testListAllEmployeeReturnConflict() throws Exception {
        Mockito.when(this.employeeService.listAllEmployee())
                .thenThrow(new ValidationException("Employee not found!"));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/listAllEmployee")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andReturn()
                .getResponse();

        Assertions.assertTrue(HttpStatus.CONFLICT.value() == response.getStatus()
                && response.getContentAsString().equalsIgnoreCase("Employee not found!"));
    }

    @Test
    @DisplayName("listAllEmployee return status code 500")
    public void testListAllEmployeeReturnInternalError() throws Exception {
        Mockito.when(this.employeeService.listAllEmployee())
                .thenThrow(new NullPointerException("null"));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/listAllEmployee")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn()
                .getResponse();

        Assertions.assertTrue(HttpStatus.INTERNAL_SERVER_ERROR.value() == response.getStatus()
                && response.getContentAsString().equalsIgnoreCase("null"));
    }

}
