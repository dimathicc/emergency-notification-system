package com.dimathicc.ens.userservice.dto;

import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String name,
        String email,
        String phone,
        String telegramId
) {}