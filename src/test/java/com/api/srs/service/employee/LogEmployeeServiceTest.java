package com.api.srs.service.employee;


import com.api.srs.ApplicationConfigTest;
import com.api.srs.dto.employee.LogEmployeeDto;
import com.api.srs.entity.employee.EmployeeEntity;
import com.api.srs.entity.employee.LogEmployeeEntity;
import com.api.srs.repository.employee.LogEmployeeRepository;
import com.api.srs.shared.DateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.IntStream;

@DisplayName("LogEmployeeServiceTest")
public class LogEmployeeServiceTest implements ApplicationConfigTest {

  @MockBean
  private LogEmployeeRepository logEmployeeRepository;

  @Autowired
  private LogEmployeeService logEmployeeService;

  @Test
  @DisplayName("Must return all employee logs")
  public void testReturnAllLogEmployee() {
    Integer idEmployee = 1;

    Mockito.when(this.logEmployeeRepository.listAllLogEmployee(idEmployee)).thenReturn(
        IntStream.range(0, 3).mapToObj(value -> new LogEmployeeDto(1, idEmployee, "Test description", DateTime.nowDate())).toList());

    this.logEmployeeService.listAllLogEmployee(idEmployee);

    Mockito.verify(this.logEmployeeRepository, Mockito.times(1)).listAllLogEmployee(idEmployee);
  }

  @Test
  @DisplayName("Must save log new employee")
  public void testSaveLogNewEmployee() {
    this.logEmployeeService.saveLogNewEmployee(Mockito.mock(EmployeeEntity.class));

    Mockito.verify(this.logEmployeeRepository, Mockito.times(1)).save(ArgumentMatchers.any(LogEmployeeEntity.class));
  }

  @Test
  @DisplayName("Must save log update employee")
  public void testSaveLogUpdateEmployee() {
    EmployeeEntity newEmployeeEntity = Mockito.mock(EmployeeEntity.class);
    Mockito.when(newEmployeeEntity.getCpf()).thenReturn("New cpf");
    Mockito.when(newEmployeeEntity.getName()).thenReturn("New name");
    Mockito.when(newEmployeeEntity.getAddress()).thenReturn("New address");
    Mockito.when(newEmployeeEntity.getEmail()).thenReturn("New email");
    Mockito.when(newEmployeeEntity.getSector()).thenReturn("New sector");
    Mockito.when(newEmployeeEntity.getPhone()).thenReturn("New phone");

    EmployeeEntity oldEmployeeEntity = Mockito.mock(EmployeeEntity.class);
    Mockito.when(oldEmployeeEntity.getCpf()).thenReturn("Old cpf");
    Mockito.when(oldEmployeeEntity.getName()).thenReturn("Old name");
    Mockito.when(oldEmployeeEntity.getAddress()).thenReturn("Old address");
    Mockito.when(oldEmployeeEntity.getEmail()).thenReturn("Old email");
    Mockito.when(oldEmployeeEntity.getSector()).thenReturn("Old sector");
    Mockito.when(oldEmployeeEntity.getPhone()).thenReturn("Old phone");

    this.logEmployeeService.saveLogUpdateEmployee(newEmployeeEntity, oldEmployeeEntity);

    Mockito.verify(this.logEmployeeRepository, Mockito.times(1)).save(ArgumentMatchers.any(LogEmployeeEntity.class));
  }

  @Test
  @DisplayName("Update has not changed")
  public void testUpdateHasNotChanged() {
    EmployeeEntity newEmployeeEntity = Mockito.mock(EmployeeEntity.class);
    Mockito.when(newEmployeeEntity.getCpf()).thenReturn("New cpf");
    Mockito.when(newEmployeeEntity.getName()).thenReturn("New name");
    Mockito.when(newEmployeeEntity.getAddress()).thenReturn("New address");
    Mockito.when(newEmployeeEntity.getEmail()).thenReturn("New email");
    Mockito.when(newEmployeeEntity.getSector()).thenReturn("New sector");
    Mockito.when(newEmployeeEntity.getPhone()).thenReturn("New phone");

    this.logEmployeeService.saveLogUpdateEmployee(newEmployeeEntity, newEmployeeEntity);

    Mockito.verify(this.logEmployeeRepository, Mockito.never()).save(ArgumentMatchers.any(LogEmployeeEntity.class));
  }

  @Test
  @DisplayName("Must save log delete employee")
  public void testSaveLogDeleteEmployee() {
    this.logEmployeeService.saveLogDeleteEmployee(Mockito.mock(EmployeeEntity.class));

    Mockito.verify(this.logEmployeeRepository, Mockito.times(1)).save(ArgumentMatchers.any(LogEmployeeEntity.class));
  }
}