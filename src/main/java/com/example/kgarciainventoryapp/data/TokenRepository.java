package com.example.kgarciainventoryapp.data;

import com.example.kgarciainventoryapp.Domain.ApiToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<ApiToken, UUID> {
    boolean existsByIdAndRevokedAtIsNullAndExpiresAtAfter(UUID id, Instant now);
}
