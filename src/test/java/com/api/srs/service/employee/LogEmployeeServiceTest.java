package com.api.srs.service.employee;


import com.api.srs.ApplicationConfigTest;
import com.api.srs.repository.employee.LogEmployeeRepository;
import com.api.srs.vo.employee.LogEmployeeVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
}
