package com.api.srs.service.employee;

import com.api.srs.dto.employee.EmployeeDto;
import com.api.srs.entity.employee.EmployeeEntity;
import com.api.srs.repository.employee.EmployeeRepository;
import com.api.srs.shared.Validator;
import com.api.srs.vo.employee.EmployeeVo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validation;
import javax.validation.ValidationException;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LogEmployeeService logEmployeeService;

    public List<EmployeeVo> listAllEmployees() {
        List<EmployeeVo> listVo = this.employeeRepository.listAllEmployees();

        if (listVo.isEmpty()) throw new ValidationException("Employees not found!");

        return listVo;
    }

    public List<EmployeeVo> listEmployeeByFilters(EmployeeDto employeeDto) {
        List<EmployeeVo> listEmployee = this.employeeRepository.listEmployeeByFilters(
                validateStringNullOrEmpty(employeeDto.getCpf()),
                validateStringNullOrEmpty(employeeDto.getName()),
                validateStringNullOrEmpty(employeeDto.getSector()),
                validateStringNullOrEmpty(employeeDto.getPhone()),
                validateStringNullOrEmpty(employeeDto.getAddress()),
                validateStringNullOrEmpty(employeeDto.getEmail())
        );

        if (listEmployee.isEmpty()) throw new ValidationException("Employee not found!");

        return listEmployee;
    }

    @Transactional
    public void saveOrUpdateEmployee(EmployeeDto employeeDto) {
        validateEmployeeDto(employeeDto);

        if (employeeDto.getId() == null)
            this.saveEmployee(employeeDto);
        else
            this.updateEmployee(employeeDto);
    }

    private void updateEmployee(EmployeeDto employeeDto) {
        EmployeeEntity employee = this.employeeRepository.getReferenceById(employeeDto.getId());
        EmployeeEntity oldEmployee =
                new EmployeeEntity
                        .Builder()
                        .cpf(employee.getCpf())
                        .name(employee.getName())
                        .address(employee.getAddress())
                        .email(employee.getEmail())
                        .phone(employee.getPhone())
                        .sector(employee.getSector())
                        .build();

        employee.setCpf(employeeDto.getCpf().trim());
        employee.setName(employeeDto.getName().trim());
        employee.setAddress(employeeDto.getAddress().trim());
        employee.setEmail(employeeDto.getEmail().trim());
        employee.setPhone(employeeDto.getPhone().trim());
        employee.setSector(employeeDto.getSector().trim());

        this.employeeRepository.saveAndFlush(employee);

        this.logEmployeeService.saveLogUpdateEmployee(employee, oldEmployee);
    }

    private void saveEmployee(EmployeeDto employeeDto) {
        EmployeeEntity employee =
                new EmployeeEntity
                        .Builder()
                        .address(employeeDto.getAddress().trim())
                        .email(employeeDto.getEmail().trim())
                        .name(employeeDto.getName().trim())
                        .cpf(employeeDto.getCpf().trim())
                        .phone(employeeDto.getPhone().trim())
                        .sector(employeeDto.getSector().trim())
                        .build();

        this.employeeRepository.saveAndFlush(employee);

        this.logEmployeeService.saveLogNewEmployee(employee);
    }

    @Transactional
    public void deleteEmployeeById(Integer id) {
        try {
            EmployeeEntity employee = this.employeeRepository.getReferenceById(id);

            this.employeeRepository.deleteById(employee.getId());

            this.logEmployeeService.saveLogDeleteEmployee(employee);
        } catch (EntityNotFoundException e) {
            throw new ValidationException("Employee not found!");
        }
    }

    private static void validateEmployeeDto(EmployeeDto employeeDto) {
        if (employeeDto.getCpf() == null || employeeDto.getCpf().isBlank() || Boolean.FALSE.equals(Validator.cpfValidator(employeeDto.getCpf())))
            throw new ValidationException("Cpf is invalid!");

        if (employeeDto.getName() == null || employeeDto.getName().isBlank())
            throw new ValidationException("Name cannot be null or empty!");

        if (employeeDto.getEmail() == null || employeeDto.getEmail().isBlank())
            throw new ValidationException("Email cannot be null or empty!");

        if (employeeDto.getPhone() == null || employeeDto.getPhone().isBlank())
            throw new ValidationException("Phone cannot be null or empty!");

        if (employeeDto.getAddress() == null || employeeDto.getAddress().isBlank())
            throw new ValidationException("Address cannot be null or empty!");

        if (employeeDto.getSector() == null || employeeDto.getSector().isBlank())
            throw new ValidationException("Sector cannot be null or empty!");
    }

    private static String validateStringNullOrEmpty(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }
}
