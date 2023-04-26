package com.api.srs.resource.employee;

import com.api.srs.resource.GenericResource;
import com.api.srs.service.employee.LogEmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logEmployee")
@RequiredArgsConstructor
public class LogEmployeeResource extends GenericResource {

    private final LogEmployeeService logEmployeeService;

    @GetMapping("/listAllLogEmployee/{employeeId}")
    @Operation(description = "List all logEmployees by employeeId")
    @ApiResponse(responseCode = "200", description = "LogEmployees successfully listed")
    public ResponseEntity<?> listAllLogEmployee(@PathVariable Integer employeeId) {
        return ResponseEntity.ok(this.logEmployeeService.listAllLogEmployee(employeeId));
    }
}
