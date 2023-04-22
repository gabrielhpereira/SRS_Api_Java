package com.api.srs.resource.user;

import com.api.srs.dto.user.UserDto;
import com.api.srs.resource.GenericResource;
import com.api.srs.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserResource extends GenericResource {

    @Autowired
    private UserService userService;

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

    @PostMapping("/saveOrUpdateUser")
    @Operation(description = "Save or update an user")
    @ApiResponse(responseCode = "200", description = "User successfully saved or updated")
    public ResponseEntity<?> saveOrUpdateUser(@RequestBody UserDto userDto) {
        this.userService.saveOrUpdateUser(userDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteUser/{id}")
    @Operation(description = "Delete an user by id")
    @ApiResponse(responseCode = "200", description = "User deleted successfully")
    public ResponseEntity<?> deleteUserById(@PathVariable Integer id) {
        this.userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}