package com.example.cco.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private long expiration;
    private Boolean requiresPasswordChange;
}
