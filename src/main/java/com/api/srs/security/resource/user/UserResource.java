package com.api.srs.security.resource.user;

import com.api.srs.resource.GenericResource;
import com.api.srs.security.dto.user.UserDto;
import com.api.srs.security.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserResource extends GenericResource {

  private final UserService userService;

  @GetMapping("/listAllUsers")
  @Operation(description = "List all users")
  @ApiResponse(responseCode = "200", description = "Users successfully listed")
  public ResponseEntity<?> listAllUsers() {
    return ResponseEntity.ok(this.userService.listAllUsers());
  }

  @PostMapping("/listUserByFilters")
  @Operation(description = "List users by filters")
  @ApiResponse(responseCode = "200", description = "Users successfully listed")
  public ResponseEntity<?> listUserByFilters(@RequestBody UserDto userDto) {
    return ResponseEntity.ok(this.userService.listUserByFilters(userDto));
  }
}