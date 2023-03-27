package com.api.srs.service.employee;

import com.api.srs.ApplicationConfigTest;
import com.api.srs.entity.employee.EmployeeEntity;
import com.api.srs.repository.employee.EmployeeRepository;
import com.api.srs.vo.employee.EmployeeVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@DisplayName("EmployeeServiceTest")
public class EmployeeServiceTest extends ApplicationConfigTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private LogEmployeeService logEmployeeService;

    @Autowired
    private EmployeeService employeeService;

    @Test
    @DisplayName("Must return all employees")
    public void mustReturnAllEmployees(){
        List<EmployeeVo> listEmployee =
                IntStream
                    .range(0, 3)
                    .mapToObj(value -> new EmployeeVo("test", "test", "test"))
                    .toList();
        Mockito.when(this.employeeRepository.listAllEmployee()).thenReturn(listEmployee);

        this.employeeService.listAllEmployee();

        Mockito.verify(this.employeeRepository, Mockito.times(1)).listAllEmployee();
    }

    @Test
    @DisplayName("Must delete an employee")
    public void mustDeleteAnEmployee(){
        String cpf = "90324214090";
        EmployeeEntity employee = Mockito.mock(EmployeeEntity.class);

        Mockito.when(employee.getCpf()).thenReturn("90324214090");
        Mockito.when(this.employeeRepository.getReferenceById(ArgumentMatchers.eq(cpf))).thenReturn(employee);

        this.employeeService.deleteEmployeeById(cpf);

        Mockito.verify(this.employeeRepository, Mockito.times(1)).deleteById(ArgumentMatchers.any());
        Mockito.verify(this.logEmployeeService, Mockito.times(1)).saveLogDeleteEmployee(ArgumentMatchers.any());
    }
}
