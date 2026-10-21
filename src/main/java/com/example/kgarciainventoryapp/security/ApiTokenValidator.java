package com.example.kgarciainventoryapp.security;

import com.example.kgarciainventoryapp.data.TokenRepository;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.UUID;

public class ApiTokenValidator implements OAuth2TokenValidator<Jwt> {
    private final TokenRepository tokenRepository;
    public ApiTokenValidator(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }


    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        String tokenId = jwt.getId();
        if (tokenId == null) {
            return failure("The token ID is missing.");
        }

        try {
            UUID id = UUID.fromString(tokenId);
            boolean active = tokenRepository.existsByIdAndRevokedAtIsNullAndExpiresAtAfter(id, Instant.now());

            if (!active) {
                return failure("The token is expired or revoked.");
            }

            return OAuth2TokenValidatorResult.success();
        }
        catch (IllegalArgumentException ex) {
            return failure("The token ID is invalid.");
        }
    }
    private OAuth2TokenValidatorResult failure(String message) {
        OAuth2Error error = new OAuth2Error("invalid_token",
                message, null);
        return OAuth2TokenValidatorResult.failure(error);
    }
}
