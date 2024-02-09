package com.dimathicc.ens.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;


public record UserRequest(
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
