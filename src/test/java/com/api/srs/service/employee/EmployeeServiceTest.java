package com.api.srs.service.employee;

import com.api.srs.ApplicationConfigTest;
import com.api.srs.dto.employee.EmployeeDto;
import com.api.srs.entity.employee.EmployeeEntity;
import com.api.srs.enums.MessageGenericEnum;
import com.api.srs.repository.employee.EmployeeRepository;
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
    List<EmployeeDto> listEmployee = IntStream.range(0, 3).mapToObj(value ->
        new EmployeeDto(1, "test", "test", "test", "test", "test", "test")).toList();
    Mockito.when(this.employeeRepository.listAllEmployees()).thenReturn(listEmployee);

    this.employeeService.listAllEmployees();

    Mockito.verify(this.employeeRepository, Mockito.times(1)).listAllEmployees();
  }

  @Test
  @DisplayName("Must return employees by filters")
  public void testReturnEmployeesByFilters() {
    List<EmployeeDto> listEmployee = IntStream.range(0, 3).mapToObj(value ->
        new EmployeeDto(1, "test", "test", "test", "test", "test", "test")).toList();

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

    Assertions.assertThrows(ValidationException.class, () -> this.employeeService.listEmployeeByFilters(buildMockDto()), MessageGenericEnum.NOT_FOUND.getMessage());

  }

  @Test
  @DisplayName("Must save an employee")
  public void testSaveAnEmployee() {
    EmployeeDto mock = buildMockDto();
    Mockito.when(mock.id()).thenReturn(null);

    this.employeeService.saveOrUpdateEmployee(mock);

    Mockito.verify(this.employeeRepository, Mockito.times(1)).saveAndFlush(ArgumentMatchers.any(EmployeeEntity.class));
    Mockito.verify(this.logEmployeeService, Mockito.times(1)).saveLogNewEmployee(ArgumentMatchers.any(EmployeeEntity.class));
  }

  @Test
  @DisplayName("Must update an employee")
  public void testUpdateAnEmployee() {
    EmployeeDto mock = buildMockDto();
    Mockito.when(this.employeeRepository.getReferenceById(mock.id()))
        .thenReturn(EmployeeEntity
            .builder()
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

    Mockito.when(mock.cpf()).thenReturn("");
    Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), MessageGenericEnum.INVALID_CPF.getMessage());

    Mockito.when(mock.cpf()).thenReturn(null);
    Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), MessageGenericEnum.INVALID_CPF.getMessage());

    Mockito.when(mock.cpf()).thenReturn("000000000");
    Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), MessageGenericEnum.INVALID_CPF.getMessage());
  }

  @Test
  @DisplayName("Must throw Exception when validation Name")
  public void testThrowExceptionWhenValidatingName() {
    EmployeeDto mock = buildMockDto();

    Mockito.when(mock.name()).thenReturn(null);
    Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), MessageGenericEnum.NAME_NULL_OR_EMPTY.getMessage());

    Mockito.when(mock.name()).thenReturn("");
    Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), MessageGenericEnum.NAME_NULL_OR_EMPTY.getMessage());
  }

  @Test
  @DisplayName("Must throw Exception when validation Email")
  public void testThrowExceptionWhenValidatingEmail() {
    EmployeeDto mock = buildMockDto();

    Mockito.when(mock.email()).thenReturn(null);
    Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), MessageGenericEnum.INVALID_EMAIL.getMessage());

    Mockito.when(mock.email()).thenReturn("");
    Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), MessageGenericEnum.INVALID_EMAIL.getMessage());

    Mockito.when(mock.email()).thenReturn("test@test");
    Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), MessageGenericEnum.INVALID_EMAIL.getMessage());
  }

  @Test
  @DisplayName("Must throw Exception when validation Phone")
  public void testThrowExceptionWhenValidatingPhone() {
    EmployeeDto mock = buildMockDto();

    Mockito.when(mock.phone()).thenReturn(null);
    Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), MessageGenericEnum.INVALID_PHONE.getMessage());

    Mockito.when(mock.phone()).thenReturn("");
    Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), MessageGenericEnum.INVALID_PHONE.getMessage());

    Mockito.when(mock.phone()).thenReturn("test");
    Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), MessageGenericEnum.INVALID_PHONE.getMessage());
  }

  @Test
  @DisplayName("Must throw Exception when validation Address")
  public void testThrowExceptionWhenValidatingAddress() {
    EmployeeDto mock = buildMockDto();

    Mockito.when(mock.address()).thenReturn(null);
    Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), MessageGenericEnum.ADDRESS_NULL_OR_EMPTY.getMessage());

    Mockito.when(mock.address()).thenReturn("");
    Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), MessageGenericEnum.ADDRESS_NULL_OR_EMPTY.getMessage());
  }

  @Test
  @DisplayName("Must throw Exception when validation Sector")
  public void testThrowExceptionWhenValidatingSector() {
    EmployeeDto mock = buildMockDto();

    Mockito.when(mock.sector()).thenReturn(null);
    Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), MessageGenericEnum.SECTOR_NULL_OR_EMPTY.getMessage());

    Mockito.when(mock.sector()).thenReturn("");
    Assertions.assertThrows(ValidationException.class, () -> this.employeeService.saveOrUpdateEmployee(mock), MessageGenericEnum.SECTOR_NULL_OR_EMPTY.getMessage());
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

    Assertions.assertThrows(ValidationException.class, () -> this.employeeService.deleteEmployeeById(id), MessageGenericEnum.NOT_FOUND.getMessage());
  }

  private static EmployeeDto buildMockDto() {
    EmployeeDto mock = Mockito.mock(EmployeeDto.class);

    Mockito.when(mock.id()).thenReturn(1);
    Mockito.when(mock.cpf()).thenReturn("90324214090");
    Mockito.when(mock.name()).thenReturn("test");
    Mockito.when(mock.email()).thenReturn("test@gmail.com");
    Mockito.when(mock.sector()).thenReturn("test");
    Mockito.when(mock.address()).thenReturn("test");
    Mockito.when(mock.phone()).thenReturn("11999999999");

    return mock;
  }
}