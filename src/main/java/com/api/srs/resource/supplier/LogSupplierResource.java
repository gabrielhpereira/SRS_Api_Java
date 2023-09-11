package com.api.srs.resource.supplier;

import com.api.srs.service.supplier.LogSupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logSupplier")
@RequiredArgsConstructor
public class LogSupplierResource {

  private final LogSupplierService logSupplierService;

  @GetMapping("/listAllLogSupplier/{supplierId}")
  @Operation(description = "List all logSupplier by supplierId")
  @ApiResponse(responseCode = "200", description = "LogSuppliers successfully listed")
  public ResponseEntity<?> listAllLogSupplier(@PathVariable Integer supplierId) {
    return ResponseEntity.ok(this.logSupplierService.listAllLogSupplier(supplierId));
  }
}
