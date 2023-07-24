package com.api.srs.resource.supplier;

import com.api.srs.dto.supplier.SupplierDto;
import com.api.srs.resource.GenericResource;
import com.api.srs.service.supplier.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supplier")
@RequiredArgsConstructor
public class SupplierResource extends GenericResource {

  private final SupplierService supplierService;

  @GetMapping("/listAllSuppliers")
  @Operation(description = "List all suppliers")
  @ApiResponse(responseCode = "200", description = "Suppliers successfully listed")
  public ResponseEntity<?> listAllSuppliers(){
    return ResponseEntity.ok(this.supplierService.listAllSuppliers());
  }

  @PostMapping("/listSupplierByFilters")
  @Operation(description = "List suppliers by filters")
  @ApiResponse(responseCode = "200", description = "Suppliers successfully listed")
  public ResponseEntity<?> listSuppliersByFilters(@RequestBody SupplierDto supplierDto){
    return ResponseEntity.ok(this.supplierService.listSuppliersByFilters(supplierDto));
  }
}
