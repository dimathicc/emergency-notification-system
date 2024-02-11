package com.dimathicc.ens.userservice.dto;

public record UserResponse(
        String name,
        String email,
        String phone,
        String telegramId
) {}