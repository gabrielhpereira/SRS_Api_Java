package com.api.srs.resource.employee;

import com.api.srs.service.employee.LogEmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;

@RestController
@RequestMapping("/logEmployee")
public class LogEmployeeResource {

    @Autowired
    private LogEmployeeService logEmployeeService;

    @GetMapping("/listAllLogEmployee/{idEmployee}")
    @Operation(description = "List all logEmployees by idEmployee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "LogEmployees successfully listed"),
            @ApiResponse(responseCode = "409", description = "LogEmployee not found!"),
            @ApiResponse(responseCode = "500", description = "An exception occurred while listing LogEmployees")
    })
    public ResponseEntity<Object> listAllLogEmployee(@PathVariable Integer idEmployee) {
        try {
            return new ResponseEntity<>(this.logEmployeeService.listAllLogEmployee(idEmployee), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
