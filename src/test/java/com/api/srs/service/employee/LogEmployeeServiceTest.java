package com.api.srs.service.employee;


import com.api.srs.ApplicationConfigTest;
import com.api.srs.entity.employee.EmployeeEntity;
import com.api.srs.entity.employee.LogEmployeeEntity;
import com.api.srs.repository.employee.LogEmployeeRepository;
import com.api.srs.vo.employee.LogEmployeeVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.stream.IntStream;

@DisplayName("LogEmployeeServiceTest")
public class LogEmployeeServiceTest extends ApplicationConfigTest {

    @MockBean
    private LogEmployeeRepository logEmployeeRepository;

    @Autowired
    private LogEmployeeService logEmployeeService;

    @Test
    @DisplayName("Must return all employee logs")
    public void mustReturnAllLogEmployee() {
        String cpf = "119929392";

        Mockito.when(this.logEmployeeRepository.listAllLogEmployee(cpf)).thenReturn(
                IntStream.range(0, 3).mapToObj(value -> new LogEmployeeVo(1, cpf,"Test description", new Date())).toList());

        this.logEmployeeService.listAllLogEmployee(cpf);

        Mockito.verify(this.logEmployeeRepository, Mockito.times(1)).listAllLogEmployee(cpf);
    }

    @Test
    @DisplayName("Must save log new employee")
    public void mustSaveLogNewEmployee(){
        this.logEmployeeService.saveLogNewEmployee(buildMockEmployeeEntity());

        Mockito.verify(this.logEmployeeRepository, Mockito.times(1)).save(ArgumentMatchers.any(LogEmployeeEntity.class));
    }

    @Test
    @DisplayName("Must save log update employee")
    public void mustSaveLogUpdateEmployee(){
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
    public void updateHasNotChanged(){
        EmployeeEntity newEmployeeEntity = Mockito.mock(EmployeeEntity.class);
        Mockito.when(newEmployeeEntity.getCpf()).thenReturn("New cpf");
        Mockito.when(newEmployeeEntity.getName()).thenReturn("New name");
        Mockito.when(newEmployeeEntity.getAddress()).thenReturn("New address");
        Mockito.when(newEmployeeEntity.getEmail()).thenReturn("New email");
        Mockito.when(newEmployeeEntity.getSector()).thenReturn("New sector");
        Mockito.when(newEmployeeEntity.getPhone()).thenReturn("New phone");

        EmployeeEntity oldEmployeeEntity = Mockito.mock(EmployeeEntity.class);
        Mockito.when(oldEmployeeEntity.getCpf()).thenReturn("New cpf");
        Mockito.when(oldEmployeeEntity.getName()).thenReturn("New name");
        Mockito.when(oldEmployeeEntity.getAddress()).thenReturn("New address");
        Mockito.when(oldEmployeeEntity.getEmail()).thenReturn("New email");
        Mockito.when(oldEmployeeEntity.getSector()).thenReturn("New sector");
        Mockito.when(oldEmployeeEntity.getPhone()).thenReturn("New phone");

        this.logEmployeeService.saveLogUpdateEmployee(newEmployeeEntity, oldEmployeeEntity);

        Mockito.verify(this.logEmployeeRepository, Mockito.never()).save(ArgumentMatchers.any(LogEmployeeEntity.class));
    }

    @Test
    @DisplayName("Must save log delete employee")
    public void mustSaveLogDeleteEmployee(){
        this.logEmployeeService.saveLogDeleteEmployee(buildMockEmployeeEntity());

        Mockito.verify(this.logEmployeeRepository, Mockito.times(1)).save(ArgumentMatchers.any(LogEmployeeEntity.class));
    }

    private EmployeeEntity buildMockEmployeeEntity(){
        EmployeeEntity mock = Mockito.mock(EmployeeEntity.class);

        Mockito.when(mock.getCpf()).thenReturn("0000000000");
        Mockito.when(mock.getName()).thenReturn("Test");

        return mock;
    }
}