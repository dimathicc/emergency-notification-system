package com.dimathicc.ens.fileservice.dto;

import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String name,
        String email,
        String phone,
        String telegramId
) {}