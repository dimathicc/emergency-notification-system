package com.dimathicc.ens.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserResponse(
        @NotNull
        String name,
        @Email
        @NotNull
        String email,
        @NotNull
        String phone,
        @NotNull
        String telegramId
) {}