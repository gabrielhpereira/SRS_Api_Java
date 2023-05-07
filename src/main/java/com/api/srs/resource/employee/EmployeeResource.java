package com.api.srs.resource.employee;

import com.api.srs.dto.employee.EmployeeDto;
import com.api.srs.resource.GenericResource;
import com.api.srs.service.employee.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@CrossOrigin
@RequiredArgsConstructor
public class EmployeeResource extends GenericResource {

    private final EmployeeService employeeService;

    @GetMapping("/listAllEmployees")
    @Operation(description = "List all employees")
    @ApiResponse(responseCode = "200", description = "Employees successfully listed")
    public ResponseEntity<?> listAllEmployees() {
        return ResponseEntity.ok(this.employeeService.listAllEmployees());
    }

    @PostMapping("/listEmployeeByFilters")
    @Operation(description = "List employee by filters")
    @ApiResponse(responseCode = "200", description = "Employees successfully listed")
    public ResponseEntity<?> listEmployeeByFilters(@RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.ok(this.employeeService.listEmployeeByFilters(employeeDto));
    }

    @PostMapping("/saveOrUpdateEmployee")
    @Operation(description = "Save or update an employee")
    @ApiResponse(responseCode = "200", description = "Employee successfully saved or updated")
    public ResponseEntity<?> saveOrUpdateEmployee(@RequestBody EmployeeDto employeeDto) {
        this.employeeService.saveOrUpdateEmployee(employeeDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteEmployee/{id}")
    @Operation(description = "Delete an employee by id")
    @ApiResponse(responseCode = "200", description = "Employee deleted successfully")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable Integer id) {
        this.employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok().build();
    }
}
