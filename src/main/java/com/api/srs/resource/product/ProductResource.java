package com.api.srs.resource.product;

import com.api.srs.dto.product.ProductDto;
import com.api.srs.resource.GenericResource;
import com.api.srs.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductResource extends GenericResource {

    private final ProductService productService;

    @GetMapping("/listAllProducts")
    @Operation(description = "List all products")
    @ApiResponse(responseCode = "200", description = "Products successfully listed")
    public ResponseEntity<?> listAllProducts() {
        return ResponseEntity.ok(this.productService.listAllProducts());
    }

    @PostMapping("/listProductByFilters")
    @Operation(description = "List products by filters")
    @ApiResponse(responseCode = "200", description = "Products successfully listed")
    public ResponseEntity<?> listProductByFilters(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(this.productService.listProductByFilters(productDto));
    }

    @PostMapping("/saveOrUpdateProduct")
    @Operation(description = "Save or update an product")
    @ApiResponse(responseCode = "200", description = "Product successfully saved or updated")
    public ResponseEntity<?> saveOrUpdateProduct(@RequestBody ProductDto productDto) {
        this.productService.saveOrUpdateProduct(productDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteProduct/{id}")
    @Operation(description = "Delete an product by id")
    @ApiResponse(responseCode = "200", description = "Product deleted successfully")
    public ResponseEntity<?> deleteProductById(@PathVariable BigInteger id) {
        this.productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }
}