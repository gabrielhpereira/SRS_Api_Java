package com.api.srs.service.employee;

import com.api.srs.dto.employee.EmployeeDto;
import com.api.srs.entity.employee.EmployeeEntity;
import com.api.srs.enums.MessageGenericEnum;
import com.api.srs.repository.employee.EmployeeRepository;

import static com.api.srs.shared.Validator.*;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final LogEmployeeService logEmployeeService;

  public List<EmployeeDto> listAllEmployees() {
    List<EmployeeDto> listDto = this.employeeRepository.listAllEmployees();

    if (listDto.isEmpty()) throw new ValidationException(MessageGenericEnum.NOT_FOUND.getMessage());

    return listDto;
  }

  public List<EmployeeDto> listEmployeeByFilters(EmployeeDto employeeDto) {
    List<EmployeeDto> listEmployee = this.employeeRepository.listEmployeeByFilters(
        validateStringNullOrEmpty(employeeDto.cpf()),
        validateStringNullOrEmpty(employeeDto.name()),
        validateStringNullOrEmpty(employeeDto.sector()),
        validateStringNullOrEmpty(employeeDto.phone()),
        validateStringNullOrEmpty(employeeDto.address()),
        validateStringNullOrEmpty(employeeDto.email())
    );

    if (listEmployee.isEmpty()) throw new ValidationException(MessageGenericEnum.NOT_FOUND.getMessage());

    return listEmployee;
  }

  @Transactional
  public void saveOrUpdateEmployee(EmployeeDto employeeDto) {
    validateEmployeeDto(employeeDto);

    if (validateIntegerNullOrZero(employeeDto.id()) == null)
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
        EmployeeEntity
            .builder()
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
      throw new ValidationException(MessageGenericEnum.NOT_FOUND.getMessage("Employee"));
    }
  }

  private static void validateEmployeeDto(EmployeeDto employeeDto) {
    if (employeeDto.cpf() == null || employeeDto.cpf().isBlank() || Boolean.FALSE.equals(cpfValidator(employeeDto.cpf())))
      throw new ValidationException(MessageGenericEnum.INVALID_CPF.getMessage());

    if (validateStringNullOrEmpty(employeeDto.name()) == null)
      throw new ValidationException(MessageGenericEnum.NAME_NULL_OR_EMPTY.getMessage());

    if (validateStringNullOrEmpty(employeeDto.email()) == null)
      throw new ValidationException(MessageGenericEnum.EMAIL_NULL_OR_EMPTY.getMessage());

    if (validateStringNullOrEmpty(employeeDto.phone()) == null)
      throw new ValidationException(MessageGenericEnum.PHONE_NULL_OR_EMPTY.getMessage());

    if (validateStringNullOrEmpty(employeeDto.address()) == null)
      throw new ValidationException(MessageGenericEnum.ADDRESS_NULL_OR_EMPTY.getMessage());

    if (validateStringNullOrEmpty(employeeDto.sector()) == null)
      throw new ValidationException(MessageGenericEnum.SECTOR_NULL_OR_EMPTY.getMessage());
  }
}
