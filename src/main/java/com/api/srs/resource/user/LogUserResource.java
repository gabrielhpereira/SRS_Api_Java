package com.api.srs.resource.user;

import com.api.srs.service.user.LogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;

@RestController
@RequestMapping("/logUser")
public class LogUserResource {

    @Autowired
    private LogUserService logUserService;

    @GetMapping("/listAllLogUser/{userId}")
    public ResponseEntity<Object> listAllLogUser(@PathVariable Integer userId) {
        try {
            return new ResponseEntity<>(this.logUserService.listAllLogUser(userId), HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}