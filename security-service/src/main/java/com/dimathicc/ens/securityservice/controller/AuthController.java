package com.dimathicc.ens.securityservice.controller;

import com.dimathicc.ens.securityservice.dto.JwtAuthenticationResponse;
import com.dimathicc.ens.securityservice.dto.SignInRequest;
import com.dimathicc.ens.securityservice.dto.SignUpRequest;
import com.dimathicc.ens.securityservice.model.User;
import com.dimathicc.ens.securityservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody @Valid SignUpRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/authenticate")
    public ResponseEntity<JwtAuthenticationResponse> authenticate(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @Operation(summary = "Валидация JWT и возврат User ID")
    @GetMapping("/validate")
    public ResponseEntity<Long> isTokenValid(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user.getId());
    }
}