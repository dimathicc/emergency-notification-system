package com.dimathicc.ens.userservice.controller;

import com.dimathicc.ens.userservice.dto.UserRequest;
import com.dimathicc.ens.userservice.dto.UserResponse;
import com.dimathicc.ens.userservice.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> retrieveAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.retrieveAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> retrieveUserById(
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.retrieveUserById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUserById(
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteById(id));
    }
}
