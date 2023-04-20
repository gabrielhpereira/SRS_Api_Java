package com.api.srs.resource.product;

import com.api.srs.dto.product.ProductDto;
import com.api.srs.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.math.BigInteger;

@RestController
@RequestMapping("/product")
public class ProductResource {

    @Autowired
    private ProductService productService;

    @GetMapping("/listAllProducts")
    @Operation(description = "List all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products successfully listed"),
            @ApiResponse(responseCode = "409", description = "Product not found!"),
            @ApiResponse(responseCode = "500", description = "An exception occurred while listing products")
    })
    public ResponseEntity<Object> listAllProducts() {
        try {
            return new ResponseEntity<>(this.productService.listAllProducts(), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/listProductByFilters")
    @Operation(description = "List products by filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products successfully listed"),
            @ApiResponse(responseCode = "409", description = "Some parameter is incorrect, validation error"),
            @ApiResponse(responseCode = "500", description = "An exception occurred while listing products"),
    })
    public ResponseEntity<Object> listProductByFilters(@RequestBody ProductDto productDto) {
        try {
            return new ResponseEntity<>(this.productService.listProductByFilters(productDto), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveOrUpdateProduct")
    @Operation(description = "Save or update an product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully saved or updated"),
            @ApiResponse(responseCode = "409", description = "Some parameter is incorrect, validation error"),
            @ApiResponse(responseCode = "500", description = "An exception occurred while saving or updating the product"),
    })
    public ResponseEntity<Object> saveProduct(@RequestBody ProductDto productDto) {
        try {
            this.productService.saveOrUpdateProduct(productDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    @Operation(description = "Delete an product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "409", description = "Product not found!"),
            @ApiResponse(responseCode = "500", description = "An exception occurred when deleting the product"),
    })
    public ResponseEntity<Object> deleteProductById(@PathVariable BigInteger id) {
        try {
            this.productService.deleteProductById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}