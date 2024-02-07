package com.dimathicc.ens.userservice.controller;

import com.dimathicc.ens.userservice.dto.UserRequest;
import com.dimathicc.ens.userservice.dto.UserResponse;
import com.dimathicc.ens.userservice.model.User;
import com.dimathicc.ens.userservice.repository.UserRepository;
import com.dimathicc.ens.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Long> createUser(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }
}
