package com.example.kgarciainventoryapp.api;

import java.time.Instant;

public class TokenResponseDTO {
    private String token;
    private String tokenType;
    private Instant expiresAt;

    public TokenResponseDTO(String token, Instant expiresAt){
        this.token = token;
        this.tokenType = "Bearer";
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
}
