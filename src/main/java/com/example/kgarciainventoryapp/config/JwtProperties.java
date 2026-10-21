package com.example.kgarciainventoryapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "item.security.jwt")
public class JwtProperties {
    private String secret;
    private String issuer = "item-registration";
    private String audience = "item-registration-api";
    private Duration tokenDuration = Duration.ofHours(8);

    public JwtProperties(){}

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public Duration getTokenDuration() {
        return tokenDuration;
    }

    public void setTokenDuration(Duration tokenDuration) {
        this.tokenDuration = tokenDuration;
    }
}
