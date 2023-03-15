package com.api.srs.service.employee;

import com.api.srs.entity.employee.EmployeeEntity;
import com.api.srs.entity.employee.LogEmployeeEntity;
import com.api.srs.entity.product.LogProductEntity;
import com.api.srs.repository.employee.LogEmployeeRepository;
import com.api.srs.vo.employee.LogEmployeeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogEmployeeService {

    @Autowired
    private LogEmployeeRepository logEmployeeRepository;

    public List<LogEmployeeVo> listAllLogEmployee(String employeeCpf) {
        return this.logEmployeeRepository.listAllLogEmployee(employeeCpf);
    }

    public void saveLogUpdateEmployee(EmployeeEntity newEmployee, EmployeeEntity oldEmployee) {
        StringBuilder sb = new StringBuilder();

        if (!newEmployee.getName().toUpperCase().trim().equals(oldEmployee.getName().toUpperCase().trim()))
            sb.append("Name changed from ")
                    .append(oldEmployee.getName().toUpperCase().trim())
                    .append(" to ")
                    .append(newEmployee.getName().toUpperCase().trim())
                    .append("\n");

        if (!newEmployee.getCpf().trim().equals(oldEmployee.getCpf().trim()))
            sb.append("Cpf changed from ")
                    .append(oldEmployee.getCpf().trim())
                    .append(" to ")
                    .append(newEmployee.getCpf().trim())
                    .append("\n");

        if (!newEmployee.getAddress().toUpperCase().trim().equals(oldEmployee.getAddress().toUpperCase().trim()))
            sb.append("Address changed from ")
                    .append(oldEmployee.getAddress().toUpperCase().trim())
                    .append(" to ")
                    .append(newEmployee.getAddress().toUpperCase().trim())
                    .append("\n");

        if (!newEmployee.getEmail().trim().equals(oldEmployee.getEmail().trim()))
            sb.append("Email changed from ")
                    .append(oldEmployee.getEmail().trim())
                    .append(" to ")
                    .append(newEmployee.getEmail().trim())
                    .append("\n");

        if (!newEmployee.getPhone().trim().equals(oldEmployee.getPhone().trim()))
            sb.append("Phone changed from ")
                    .append(oldEmployee.getPhone().trim())
                    .append(" to ")
                    .append(newEmployee.getPhone().trim())
                    .append("\n");

        if (!newEmployee.getSector().toUpperCase().trim().equals(oldEmployee.getSector().toUpperCase().trim()))
            sb.append("Sector changed from ")
                    .append(oldEmployee.getSector().toUpperCase().trim())
                    .append(" to ")
                    .append(newEmployee.getSector().toUpperCase().trim())
                    .append("\n");

        if (!sb.toString().isEmpty())
            this.logEmployeeRepository.save(
                    new LogEmployeeEntity
                            .Builder()
                            .employeeCpf(newEmployee.getCpf())
                            .description("Product " + newEmployee.getCpf() + " : \n\n" + sb.toString())
                            .date(now())
                            .build());
    }

    public void saveLogNewEmployee(EmployeeEntity employee) {
        this.logEmployeeRepository.save(
                new LogEmployeeEntity
                        .Builder()
                        .employeeCpf(employee.getCpf())
                        .description("Employee " + employee.getCpf() + " - " + employee.getName().toUpperCase().trim() + " has been created!")
                        .date(now())
                        .build()
        );
    }

    public void saveLogDeleteEmployee(EmployeeEntity employee) {
        this.logEmployeeRepository.save(
                new LogEmployeeEntity
                        .Builder()
                        .employeeCpf(employee.getCpf())
                        .description("Employee " + employee.getCpf() + " - " + employee.getName().toUpperCase().trim() + " has been deleted!")
                        .date(now())
                        .build()
        );
    }

    private static Date now() {
        return new Date();
    }
}
