package com.api.srs.security.resource.auth;

import com.api.srs.resource.GenericResource;
import com.api.srs.security.dto.token.TokenDto;
import com.api.srs.security.dto.user.UserDto;
import com.api.srs.security.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthResource extends GenericResource {

  private final AuthService authService;

  @PostMapping("/register")
  @Operation(description = "Register a new user")
  @ApiResponse(responseCode = "200", description = "Successfully registered user")
  public ResponseEntity<TokenDto> registerNewUser(@RequestBody UserDto userDto) {
    return ResponseEntity.ok(this.authService.registerNewUser(userDto));
  }

  @PostMapping("/authenticate")
  @Operation(description = "Log in a user")
  @ApiResponse(responseCode = "200", description = "Successful login")
  public ResponseEntity<TokenDto> authenticate(@RequestBody UserDto userDto) {
    return ResponseEntity.ok(this.authService.authenticate(userDto));
  }

  @PostMapping("/refreshToken")
  @Operation(description = "Update login token")
  @ApiResponse(responseCode = "200", description = "Successfully updated login token")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    this.authService.refreshToken(request, response);
  }
}
