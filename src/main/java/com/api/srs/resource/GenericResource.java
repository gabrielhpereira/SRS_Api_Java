package com.api.srs.resource;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;

public class GenericResource {
  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  @ApiResponse(responseCode = "409", description = "Some parameter is incorrect, validation error")
  public String handleValidationException(ValidationException ex) {
    return ex.getMessage();
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ApiResponse(responseCode = "500", description = "An exception occurred")
  public String handleException(Exception ex) {
    return ex.getMessage();
  }
}
