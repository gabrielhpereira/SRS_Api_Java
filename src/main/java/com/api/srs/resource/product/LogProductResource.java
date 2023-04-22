package com.api.srs.resource.product;

import com.api.srs.resource.GenericResource;
import com.api.srs.service.product.LogProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping("/logProduct")
public class LogProductResource extends GenericResource {

    @Autowired
    private LogProductService logProductService;

    @GetMapping("/listAllLogProduct/{productId}")
    @Operation(description = "List all logProduct by productId")
    @ApiResponse(responseCode = "200", description = "LogProducts successfully listed")
    public ResponseEntity<?> listAllLogEmployee(@PathVariable BigInteger productId) {
        return ResponseEntity.ok(this.logProductService.listAllLogProduct(productId));
    }
}