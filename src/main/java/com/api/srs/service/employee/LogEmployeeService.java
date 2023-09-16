package com.api.srs.service.employee;

import static com.api.srs.shared.GenericLog.*;

import com.api.srs.dto.employee.LogEmployeeDto;
import com.api.srs.entity.employee.EmployeeEntity;
import com.api.srs.entity.employee.LogEmployeeEntity;
import com.api.srs.repository.employee.LogEmployeeRepository;
import com.api.srs.shared.DateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogEmployeeService {

  private final LogEmployeeRepository logEmployeeRepository;

  public List<LogEmployeeDto> listAllLogEmployee(Integer employeeId) {
    return this.logEmployeeRepository.listAllLogEmployee(employeeId);
  }

  protected void saveLogUpdateEmployee(EmployeeEntity newEmployee, EmployeeEntity oldEmployee) {
    StringBuilder sb = new StringBuilder();

    sb.append(updateEntity(newEmployee.getName(), oldEmployee.getName(), "Name"));
    sb.append(updateEntity(newEmployee.getCpf(), oldEmployee.getCpf(), "Cpf"));
    sb.append(updateEntity(newEmployee.getAddress(), oldEmployee.getAddress(), "Address"));
    sb.append(updateEntity(newEmployee.getEmail(), oldEmployee.getEmail(), "Email"));
    sb.append(updateEntity(newEmployee.getPhone(), oldEmployee.getPhone(), "Phone"));
    sb.append(updateEntity(newEmployee.getSector(), oldEmployee.getSector(), "Sector"));

    if (!sb.toString().isEmpty())
      this.logEmployeeRepository.save(
          LogEmployeeEntity
              .builder()
              .employeeId(newEmployee.getId())
              .description("Product " + newEmployee.getCpf() + " : \n\n" + sb.toString())
              .date(DateTime.nowDate())
              .build());
  }

  protected void saveLogNewEmployee(EmployeeEntity employee) {
    this.logEmployeeRepository.save(
        LogEmployeeEntity
            .builder()
            .id(employee.getId())
            .description("Employee " + employee.getCpf() + " - " + employee.getName() + " has been created!")
            .date(DateTime.nowDate())
            .build()
    );
  }

  protected void saveLogDeleteEmployee(EmployeeEntity employee) {
    this.logEmployeeRepository.save(
        LogEmployeeEntity
            .builder()
            .employeeId(employee.getId())
            .description("Employee " + employee.getCpf() + " - " + employee.getName() + " has been deleted!")
            .date(DateTime.nowDate())
            .build()
    );
  }
}
