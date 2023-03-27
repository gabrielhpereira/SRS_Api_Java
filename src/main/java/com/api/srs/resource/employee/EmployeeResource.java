package com.api.srs.resource.employee;

import com.api.srs.dto.employee.EmployeeDto;
import com.api.srs.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

@RestController
@RequestMapping("/employee")
public class EmployeeResource {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/listAllEmployee")
    public ResponseEntity<Object> listAllEmployee() {
        try {
            return new ResponseEntity<>(this.employeeService.listAllEmployee(), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/listEmployeeByFilters")
    public ResponseEntity<Object> listEmployeeByFilters(@RequestBody EmployeeDto employeeDto) {
        try {
            return new ResponseEntity<>(this.employeeService.listEmployeeByFilters(employeeDto), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveOrUpdateEmployee")
    public ResponseEntity<Object> saveOrUpdateEmployee(@RequestBody EmployeeDto employeeDto) {
        try {
            this.employeeService.saveOrUpdateEmployee(employeeDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteEmployee/{cpf}")
    public ResponseEntity<Object> deleteEmployeeById(@PathVariable String cpf) {
        try {
            this.employeeService.deleteEmployeeById(cpf);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
