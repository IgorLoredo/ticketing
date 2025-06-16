package com.example.Ticketing.Controller;


import com.example.Ticketing.Model.DTO.Request.UserRequest;
import com.example.Ticketing.Model.DTO.Response.UserResponse;
import com.example.Ticketing.Service.UserService;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/by-email")
    public ResponseEntity<UserResponse> getUserByEmail(
            @RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }
}