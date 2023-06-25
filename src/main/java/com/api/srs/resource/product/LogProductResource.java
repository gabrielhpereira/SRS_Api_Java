package com.api.srs.resource.product;

import com.api.srs.resource.GenericResource;
import com.api.srs.service.product.LogProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/logProduct")
@RequiredArgsConstructor
public class LogProductResource extends GenericResource {

  private final LogProductService logProductService;

  @GetMapping("/listAllLogProduct/{productId}")
  @Operation(description = "List all logProduct by productId")
  @ApiResponse(responseCode = "200", description = "LogProducts successfully listed")
  public ResponseEntity<?> listAllLogEmployee(@PathVariable BigInteger productId) {
    return ResponseEntity.ok(this.logProductService.listAllLogProduct(productId));
  }
}