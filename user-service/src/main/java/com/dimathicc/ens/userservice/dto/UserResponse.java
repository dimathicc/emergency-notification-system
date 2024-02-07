package com.dimathicc.ens.userservice.dto;

import jakarta.validation.constraints.Email;

public record UserResponse(
        String name,
        @Email
        String email,
        String phone,
        String telegramId) {
}