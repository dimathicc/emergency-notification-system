package com.dimathicc.ens.fileservice.dto;

import lombok.Builder;

@Builder
public record UserRequest(
        String name,
        String email,
        String phone,
        String telegramId
) {}
