package com.example.kgarciainventoryapp.security;

import com.example.kgarciainventoryapp.api.LoginRequestDTO;
import com.example.kgarciainventoryapp.api.TokenResponseDTO;
import com.example.kgarciainventoryapp.services.ApiAuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {
    private final ApiAuthService authService;

    public ApiAuthController(ApiAuthService aas) {
        authService = aas;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        try {
            TokenResponseDTO response = authService.login(request);
            return ResponseEntity.ok(response);
        }
        catch(AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
    @PostMapping("/logout")
    @SecurityRequirement(name="bearerAuth")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal Jwt jwt) {
        UUID tokenId = UUID.fromString(jwt.getId());

        authService.revokeToken(tokenId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegistrationRequestDTO request) {
        try {
            Optional<User> registeredUser = authService.register(request);

            // username already exists - blank
            if (registeredUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDTO.from(registeredUser.get()));

        }
        catch(IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    } **/
}
