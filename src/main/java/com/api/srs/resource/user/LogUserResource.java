package com.api.srs.resource.user;

import com.api.srs.resource.GenericResource;
import com.api.srs.service.user.LogUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logUser")
public class LogUserResource extends GenericResource {

    @Autowired
    private LogUserService logUserService;

    @GetMapping("/listAllLogUser/{userId}")
    @Operation(description = "List all logUsers by userId")
    @ApiResponse(responseCode = "200", description = "LogUsers successfully listed")
    public ResponseEntity<?> listAllLogUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(this.logUserService.listAllLogUser(userId));
    }
}