package com.api.srs.service.employee;

import com.api.srs.dto.employee.EmployeeDto;
import com.api.srs.entity.employee.EmployeeEntity;
import com.api.srs.repository.employee.EmployeeRepository;
import com.api.srs.shared.Validator;
import com.api.srs.vo.employee.EmployeeVo;
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

    public List<EmployeeVo> listAllEmployee() {
        return this.employeeRepository.listAllEmployee();
    }

    public List<EmployeeVo> listEmployeeByFilters(EmployeeDto employeeDto) {
        validateEmployeeDto(employeeDto);
        return this.employeeRepository.listEmployeeByFilters(
                employeeDto.getCpf(),
                employeeDto.getName(),
                employeeDto.getSector()
        );
    }

    @Transactional
    public void saveOrUpdateEmployee(EmployeeDto employeeDto) {
        validateEmployeeDto(employeeDto);

        if (employeeDto.getCpf().isBlank() || employeeDto.getCpf() == null)
            this.saveEmployee(employeeDto);
        else
            this.updateEmployee(employeeDto);
    }

    private void updateEmployee(EmployeeDto employeeDto) {
        EmployeeEntity employee = this.employeeRepository.getReferenceById(employeeDto.getCpf());
        EmployeeEntity oldEmployee =
                new EmployeeEntity
                        .Builder()
                        .name(employee.getName())
                        .address(employee.getAddress())
                        .email(employee.getEmail())
                        .phone(employee.getPhone())
                        .sector(employee.getSector())
                        .build();

        employee.setName(employeeDto.getName().toUpperCase().trim());
        employee.setAddress(employeeDto.getAddress().toUpperCase().trim());
        employee.setEmail(employeeDto.getEmail().trim());
        employee.setPhone(employeeDto.getPhone().trim());
        employee.setSector(employeeDto.getSector().toUpperCase().trim());

        this.employeeRepository.saveAndFlush(employee);

        this.logEmployeeService.saveLogUpdateEmployee(employee, oldEmployee);
    }

    private void saveEmployee(EmployeeDto employeeDto) {
        EmployeeEntity employee =
                new EmployeeEntity
                        .Builder()
                        .address(employeeDto.getAddress())
                        .email(employeeDto.getEmail())
                        .name(employeeDto.getName())
                        .cpf(employeeDto.getCpf())
                        .phone(employeeDto.getPhone())
                        .sector(employeeDto.getSector())
                        .build();

        this.employeeRepository.saveAndFlush(employee);

        this.logEmployeeService.saveLogNewEmployee(employee);
    }

    @Transactional
    public void deleteEmployeeById(String cpf) {
        EmployeeEntity employee = this.employeeRepository.getReferenceById(cpf);

        this.employeeRepository.deleteById(employee.getCpf());

        this.logEmployeeService.saveLogDeleteEmployee(employee);
    }

    private static void validateEmployeeDto(EmployeeDto employeeDto) {
        if (Boolean.FALSE.equals(Validator.cpfValidator(employeeDto.getCpf())))
            throw new ValidationException("Cpf is invalid!");

        if(employeeDto.getName() == null || employeeDto.getName().isBlank())
            throw new ValidationException("Name cannot be null or empty!");

        if(employeeDto.getEmail() == null || employeeDto.getEmail().isBlank())
            throw new ValidationException("Email cannot be null or empty!");

        if(employeeDto.getPhone() == null || employeeDto.getPhone().isBlank())
            throw new ValidationException("Phone cannot be null or empty!");
    }
}
