package com.api.srs.resource.product;

import com.api.srs.service.product.LogProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import java.math.BigInteger;

@RestController
@RequestMapping("/logProduct")
public class LogProductResource {

    @Autowired
    private LogProductService logProductService;

    @GetMapping("/listAllLogProduct/{productId}")
    public ResponseEntity<Object> listAllLogProduct(@PathVariable BigInteger productId) {
        try {
            return new ResponseEntity<>(this.logProductService.listAllLogProduct(productId), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}