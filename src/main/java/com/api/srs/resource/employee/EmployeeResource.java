package com.api.srs.resource.employee;

import com.api.srs.dto.employee.EmployeeDto;
import com.api.srs.service.employee.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @GetMapping("/listAllEmployees")
    @Operation(description = "List all employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees successfully listed"),
            @ApiResponse(responseCode = "409", description = "Employee not found!"),
            @ApiResponse(responseCode = "500", description = "An exception occurred while listing employees")
    })
    public ResponseEntity<Object> listAllEmployees() {
        try {
            return new ResponseEntity<>(this.employeeService.listAllEmployees(), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/listEmployeeByFilters")
    @Operation(description = "List employee by filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees successfully listed"),
            @ApiResponse(responseCode = "409", description = "Some parameter is incorrect, validation error"),
            @ApiResponse(responseCode = "500", description = "An exception occurred while listing employees"),
    })
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
    @Operation(description = "Save or update an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee successfully saved or updated"),
            @ApiResponse(responseCode = "409", description = "Some parameter is incorrect, validation error"),
            @ApiResponse(responseCode = "500", description = "An exception occurred while saving or updating the employee"),
    })
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

    @DeleteMapping("/deleteEmployee/{id}")
    @Operation(description = "Delete an employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee deleted successfully"),
            @ApiResponse(responseCode = "409", description = "Employee not found!"),
            @ApiResponse(responseCode = "500", description = "An exception occurred when deleting the employee"),
    })
    public ResponseEntity<Object> deleteEmployeeById(@PathVariable Integer id) {
        try {
            this.employeeService.deleteEmployeeById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
