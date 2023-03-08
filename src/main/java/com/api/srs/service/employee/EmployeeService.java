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

        this.employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployeeById(Integer cpf) {
        this.employeeRepository.deleteById(cpf);
    }

    private static void validateEmployeeDto(EmployeeDto employeeDto) {
        if (Boolean.FALSE.equals(Validator.cpfValidator(employeeDto.getCpf())))
            throw new ValidationException("Cpf is invalid!");
    }
}
