package com.api.srs.service.employee;

import com.api.srs.ApplicationConfigTest;
import com.api.srs.dto.employee.EmployeeDto;
import com.api.srs.entity.employee.EmployeeEntity;
import com.api.srs.repository.employee.EmployeeRepository;
import com.api.srs.vo.employee.EmployeeVo;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@DisplayName("EmployeeServiceTest")
public class EmployeeServiceTest implements ApplicationConfigTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private LogEmployeeService logEmployeeService;

    @Autowired
    private EmployeeService employeeService;

    @Test
    @DisplayName("Must return all employees")
    public void testReturnAllEmployees() {
        List<EmployeeVo> listEmployee = IntStream.range(0, 3).mapToObj(value ->
                new EmployeeVo(1, "test", "test", "test", "test", "test", "test")).toList();
        Mockito.when(this.employeeRepository.listAllEmployees()).thenReturn(listEmployee);

        this.employeeService.listAllEmployees();

        Mockito.verify(this.employeeRepository, Mockito.times(1)).listAllEmployees();
    }

    @Test
    @DisplayName("Must return employees by filters")
    public void testReturnEmployeesByFilters() {
        List<EmployeeVo> listEmployee = IntStream.range(0, 3).mapToObj(value ->
                new EmployeeVo(1, "test", "test", "test", "test", "test", "test")).toList();

        Mockito.when(this.employeeRepository
                .listEmployeeByFilters(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                )
        ).thenReturn(listEmployee);

        this.employeeService.listEmployeeByFilters(buildMockDto());

        Mockito.verify(this.employeeRepository, Mockito.times(1))
                .listEmployeeByFilters(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }

    @Test
    @DisplayName("Must throw exception when return empty list")
    public void testThrowExceptionWhenReturnEmptyList() {
        Mockito.when(this.employeeRepository
                .listEmployeeByFilters(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                )
        ).thenReturn(new ArrayList<>());

        Assertions.assertThrows(ValidationException.class, () -> this.employeeService.listEmployeeByFilters(buildMockDto()), "Employee not found!");

    }

    @Test
    @DisplayName("Must save an employee")
    public void testSaveAnEmployee() {
        EmployeeDto mock = buildMockDto();
        Mockito.when(mock.getId()).thenReturn(null);

        this.employeeService.saveOrUpdateEmployee(mock);

        Mockito.verify(this.employeeRepository, Mockito.times(1)).saveAndFlush(ArgumentMatchers.any(EmployeeEntity.class));
        Mockito.verify(this.logEmployeeService, Mockito.times(1)).saveLogNewEmployee(ArgumentMatchers.any(EmployeeEntity.class));
    }

    @Test
    @DisplayName("Must update an employee")
    public void testUpdateAnEmployee() {
        EmployeeDto mock = buildMockDto();
        Mockito.when(this.employeeRepository.getReferenceById(mock.getId()))
                .thenReturn(new EmployeeEntity
                        .Builder()
                        .id(1)
                        .cpf("90324214090")
                        .name("test")
                        .email("test@gmail.com")
                        .address("test")
                        .phone("111111111")
                        .sector("test")
                        .build()
                );

        this.employeeService.saveOrUpdateEmployee(mock);

        Mockito.verify(this.employeeRepository, Mockito.times(1)).saveAndFlush(ArgumentMatchers.any(EmployeeEntity.class));
        Mockito.verify(this.logEmployeeService, Mockito.times(1))
                .saveLogUpdateEmployee(ArgumentMatchers.any(EmployeeEntity.class), ArgumentMatchers.any(EmployeeEntity.class));
    }

    @Test
    @DisplayName("Must throw Exception when validation Cpf")
    public void testThrowExceptionWhenValidatingCpf() {
        EmployeeDto mock = Mockito.mock(EmployeeDto.class);

        Mockito.when(mock.getCpf()).thenReturn("");

        Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), "Cpf is invalid!");

        Mockito.when(mock.getCpf()).thenReturn(null);

        Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), "Cpf is invalid!");

        Mockito.when(mock.getCpf()).thenReturn("000000000");

        Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), "Cpf is invalid!");
    }

    @Test
    @DisplayName("Must throw Exception when validation Name")
    public void testThrowExceptionWhenValidatingName() {
        EmployeeDto mock = buildMockDto();

        Mockito.when(mock.getName()).thenReturn(null);

        Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), "Name cannot be null or empty!");

        Mockito.when(mock.getName()).thenReturn("");

        Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), "Name cannot be null or empty!");
    }

    @Test
    @DisplayName("Must throw Exception when validation Email")
    public void testThrowExceptionWhenValidatingEmail() {
        EmployeeDto mock = buildMockDto();

        Mockito.when(mock.getEmail()).thenReturn(null);

        Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), "Email cannot be null or empty!");

        Mockito.when(mock.getEmail()).thenReturn("");

        Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), "Email cannot be null or empty!");
    }

    @Test
    @DisplayName("Must throw Exception when validation Phone")
    public void testThrowExceptionWhenValidatingPhone() {
        EmployeeDto mock = buildMockDto();

        Mockito.when(mock.getPhone()).thenReturn(null);

        Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), "Phone cannot be null or empty!");

        Mockito.when(mock.getPhone()).thenReturn("");

        Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), "Phone cannot be null or empty!");
    }

    @Test
    @DisplayName("Must throw Exception when validation Address")
    public void testThrowExceptionWhenValidatingAddress() {
        EmployeeDto mock = buildMockDto();

        Mockito.when(mock.getAddress()).thenReturn(null);

        Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), "Address cannot be null or empty!");

        Mockito.when(mock.getAddress()).thenReturn("");

        Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), "Address cannot be null or empty!");
    }

    @Test
    @DisplayName("Must throw Exception when validation Sector")
    public void testThrowExceptionWhenValidatingSector() {
        EmployeeDto mock = buildMockDto();

        Mockito.when(mock.getSector()).thenReturn(null);

        Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), "Sector cannot be null or empty!");

        Mockito.when(mock.getSector()).thenReturn("");

        Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), "Sector cannot be null or empty!");
    }

    @Test
    @DisplayName("Must delete an employee")
    public void testDeleteAnEmployee() {
        Integer id = 1;
        EmployeeEntity mock = Mockito.mock(EmployeeEntity.class);

        Mockito.when(mock.getId()).thenReturn(id);
        Mockito.when(this.employeeRepository.getReferenceById(id)).thenReturn(mock);

        this.employeeService.deleteEmployeeById(id);

        Mockito.verify(this.employeeRepository, Mockito.times(1)).deleteById(ArgumentMatchers.any());
        Mockito.verify(this.logEmployeeService, Mockito.times(1)).saveLogDeleteEmployee(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Must throw Exception employee not found")
    public void testDeleteAnEmployeeNotFound() {
        Integer id = 1;
        EmployeeEntity mock = Mockito.mock(EmployeeEntity.class);

        Mockito.when(mock.getId()).thenReturn(id);
        Mockito.when(this.employeeRepository.getReferenceById(id)).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(ValidationException.class, () -> this.employeeService.deleteEmployeeById(id), "Employee not found!");
    }

    private static EmployeeDto buildMockDto() {
        EmployeeDto mock = Mockito.mock(EmployeeDto.class);

        Mockito.when(mock.getId()).thenReturn(1);
        Mockito.when(mock.getCpf()).thenReturn("90324214090");
        Mockito.when(mock.getName()).thenReturn("test");
        Mockito.when(mock.getEmail()).thenReturn("test@gmail.com");
        Mockito.when(mock.getSector()).thenReturn("test");
        Mockito.when(mock.getAddress()).thenReturn("test");
        Mockito.when(mock.getPhone()).thenReturn("123456789");

        return mock;
    }
}
