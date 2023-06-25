package com.api.srs.service.employee;

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

  public void saveLogUpdateEmployee(EmployeeEntity newEmployee, EmployeeEntity oldEmployee) {
    StringBuilder sb = new StringBuilder();

    if (!newEmployee.getName().equalsIgnoreCase(oldEmployee.getName()))
      sb.append("Name changed from ")
          .append(oldEmployee.getName().toUpperCase())
          .append(" to ")
          .append(newEmployee.getName().toUpperCase())
          .append("\n");

    if (!newEmployee.getCpf().equals(oldEmployee.getCpf()))
      sb.append("Cpf changed from ")
          .append(oldEmployee.getCpf())
          .append(" to ")
          .append(newEmployee.getCpf())
          .append("\n");

    if (!newEmployee.getAddress().equalsIgnoreCase(oldEmployee.getAddress()))
      sb.append("Address changed from ")
          .append(oldEmployee.getAddress().toUpperCase())
          .append(" to ")
          .append(newEmployee.getAddress().toUpperCase())
          .append("\n");

    if (!newEmployee.getEmail().equalsIgnoreCase(oldEmployee.getEmail()))
      sb.append("Email changed from ")
          .append(oldEmployee.getEmail())
          .append(" to ")
          .append(newEmployee.getEmail())
          .append("\n");

    if (!newEmployee.getPhone().equals(oldEmployee.getPhone()))
      sb.append("Phone changed from ")
          .append(oldEmployee.getPhone())
          .append(" to ")
          .append(newEmployee.getPhone())
          .append("\n");

    if (!newEmployee.getSector().equalsIgnoreCase(oldEmployee.getSector()))
      sb.append("Sector changed from ")
          .append(oldEmployee.getSector().toUpperCase())
          .append(" to ")
          .append(newEmployee.getSector().toUpperCase())
          .append("\n");

    if (!sb.toString().isEmpty())
      this.logEmployeeRepository.save(
          LogEmployeeEntity
              .builder()
              .employeeId(newEmployee.getId())
              .description("Product " + newEmployee.getCpf() + " : \n\n" + sb.toString())
              .date(DateTime.nowDate())
              .build());
  }

  public void saveLogNewEmployee(EmployeeEntity employee) {
    this.logEmployeeRepository.save(
        LogEmployeeEntity
            .builder()
            .id(employee.getId())
            .description("Employee " + employee.getCpf() + " - " + employee.getName() + " has been created!")
            .date(DateTime.nowDate())
            .build()
    );
  }

  public void saveLogDeleteEmployee(EmployeeEntity employee) {
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
