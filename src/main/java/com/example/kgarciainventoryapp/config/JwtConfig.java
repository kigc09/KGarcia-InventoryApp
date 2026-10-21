package com.example.kgarciainventoryapp.config;

import com.example.kgarciainventoryapp.data.TokenRepository;
import com.example.kgarciainventoryapp.security.ApiTokenValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class JwtConfig {
    @Bean
    public SecretKey jwtSecretKey(JwtProperties properties){
        byte[] keyBytes = Base64.getDecoder()
                .decode(properties.getSecret());

        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    @Bean
    public JwtEncoder jwtEncoder(SecretKey secretKey){

        return NimbusJwtEncoder.withSecretKey(secretKey)
                .algorithm(MacAlgorithm.HS256).build();
    }

    @Bean
    public JwtDecoder jwtDecoder(SecretKey secretKey, JwtProperties properties, TokenRepository tokenRepository){
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256).build();

        OAuth2TokenValidator<Jwt> standardValidation = JwtValidators.createDefaultWithIssuer(properties.getIssuer());

        OAuth2TokenValidator<Jwt> audienceValidation = new JwtAudienceValidator(properties.getAudience());

        OAuth2TokenValidator<Jwt> databaseValidation = new ApiTokenValidator(tokenRepository);

        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(standardValidation, audienceValidation, databaseValidation));


        return decoder;
    }
}
