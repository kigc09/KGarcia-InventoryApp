package com.example.kgarciainventoryapp.services;

import com.example.kgarciainventoryapp.Domain.ApiToken;
import com.example.kgarciainventoryapp.Domain.User;
import com.example.kgarciainventoryapp.api.LoginRequestDTO;
import com.example.kgarciainventoryapp.api.TokenResponseDTO;
import com.example.kgarciainventoryapp.api.UserRegistrationRequestDTO;
import com.example.kgarciainventoryapp.config.JwtProperties;
import com.example.kgarciainventoryapp.data.TokenRepository;
import com.example.kgarciainventoryapp.data.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ApiAuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final JwtProperties jwtProperties;

    public ApiAuthService(
            UserRepository userRepository,
            TokenRepository tokenRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtEncoder jwtEncoder,
            JwtProperties jwtProperties) {

        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager =
                authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.jwtProperties = jwtProperties;
    }

    @Transactional
    public TokenResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        User user = (User) authentication.getPrincipal();

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(jwtProperties.getTokenDuration());

        UUID tokenId = UUID.randomUUID();

        List<String> roles = user.getAuthorities().stream().map(authority ->
                authority.getAuthority()).toList();

        JwtClaimsSet claims = JwtClaimsSet.builder().issuer(jwtProperties.getIssuer())
                .subject(user.getUsername())
                .audience(List.of(jwtProperties.getAudience()))
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .id(tokenId.toString())
                .claim("userId",user.getId().toString())
                .claim("roles", roles)
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).type("JWT").build();

        String tokenValue = jwtEncoder.encode(JwtEncoderParameters.from(header, claims))
                .getTokenValue();

        ApiToken tokenRecord = new ApiToken(tokenId, user, issuedAt, expiresAt);
        tokenRepository.save(tokenRecord);

        return new TokenResponseDTO(tokenValue, expiresAt);
    }

    @Transactional
    public boolean revokeToken(UUID tokenId) {
        Optional<ApiToken> lookupToken = tokenRepository.findById(tokenId);

        if (lookupToken.isEmpty()) {
            return false;
        }

        ApiToken token = lookupToken.get();
        token.revoke();

        tokenRepository.save(token);

        return true;
    }

    public Optional<User> register(@Valid UserRegistrationRequestDTO request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords must match.");
        }

        String username = request.getUsername().trim();

        if (userRepository.existsByUsername(username)) {
            return Optional.empty();
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(username, request.getFullname(),
                request.getEmail(), "ROLE_USER");

        return Optional.of(userRepository.save(user));
    }
}
