package com.dimathicc.ens.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;


public record UserRequest(
        @NotBlank(message = "Имя не должно быть пустым")
        String name,
        @Email
        @NotBlank(message = "Почта не должна быть пустой")
        String email,
        @NotBlank(message = "Телефон не должен быть пустым")
        String phone,
        @NotBlank(message = "Telegram ID не должен быть пустым")
        String telegramId
) {}
