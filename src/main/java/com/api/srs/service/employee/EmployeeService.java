package com.api.srs.service.employee;

import com.api.srs.dto.employee.EmployeeDto;
import com.api.srs.entity.employee.EmployeeEntity;
import com.api.srs.repository.employee.EmployeeRepository;
import com.api.srs.shared.Validator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LogEmployeeService logEmployeeService;

    public List<EmployeeDto> listAllEmployees() {
        List<EmployeeDto> listVo = this.employeeRepository.listAllEmployees();

        if (listVo.isEmpty()) throw new ValidationException("Employees not found!");

        return listVo;
    }

    public List<EmployeeDto> listEmployeeByFilters(EmployeeDto employeeDto) {
        List<EmployeeDto> listEmployee = this.employeeRepository.listEmployeeByFilters(
                Validator.validateStringNullOrEmpty(employeeDto.cpf()),
                Validator.validateStringNullOrEmpty(employeeDto.name()),
                Validator.validateStringNullOrEmpty(employeeDto.sector()),
                Validator.validateStringNullOrEmpty(employeeDto.phone()),
                Validator.validateStringNullOrEmpty(employeeDto.address()),
                Validator.validateStringNullOrEmpty(employeeDto.email())
        );

        if (listEmployee.isEmpty()) throw new ValidationException("Employee not found!");

        return listEmployee;
    }

    @Transactional
    public void saveOrUpdateEmployee(EmployeeDto employeeDto) {
        validateEmployeeDto(employeeDto);

        if (employeeDto.id() == null)
            this.saveEmployee(employeeDto);
        else
            this.updateEmployee(employeeDto);
    }

    private void updateEmployee(EmployeeDto employeeDto) {
        EmployeeEntity employee = this.employeeRepository.getReferenceById(employeeDto.id());
        EmployeeEntity oldEmployee = new EmployeeEntity(employee);

        employee.setCpf(employeeDto.cpf().trim());
        employee.setName(employeeDto.name().trim());
        employee.setAddress(employeeDto.address().trim());
        employee.setEmail(employeeDto.email().trim());
        employee.setPhone(employeeDto.phone().trim());
        employee.setSector(employeeDto.sector().trim());

        this.employeeRepository.saveAndFlush(employee);

        this.logEmployeeService.saveLogUpdateEmployee(employee, oldEmployee);
    }

    private void saveEmployee(EmployeeDto employeeDto) {
        EmployeeEntity employee =
                new EmployeeEntity
                        .Builder()
                        .address(employeeDto.address().trim())
                        .email(employeeDto.email().trim())
                        .name(employeeDto.name().trim())
                        .cpf(employeeDto.cpf().trim())
                        .phone(employeeDto.phone().trim())
                        .sector(employeeDto.sector().trim())
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
        if (employeeDto.cpf() == null || employeeDto.cpf().isBlank() || Boolean.FALSE.equals(Validator.cpfValidator(employeeDto.cpf())))
            throw new ValidationException("Cpf is invalid!");

        if (employeeDto.name() == null || employeeDto.name().isBlank())
            throw new ValidationException("Name cannot be null or empty!");

        if (employeeDto.email() == null || employeeDto.email().isBlank())
            throw new ValidationException("Email cannot be null or empty!");

        if (employeeDto.phone() == null || employeeDto.phone().isBlank())
            throw new ValidationException("Phone cannot be null or empty!");

        if (employeeDto.address() == null || employeeDto.address().isBlank())
            throw new ValidationException("Address cannot be null or empty!");

        if (employeeDto.sector() == null || employeeDto.sector().isBlank())
            throw new ValidationException("Sector cannot be null or empty!");
    }
}
