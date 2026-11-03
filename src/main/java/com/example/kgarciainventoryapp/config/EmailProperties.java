package com.example.kgarciainventoryapp.config;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "item.email")
@Validated
public class EmailProperties {

    @NotBlank
    @Email
    private String fromAddress;

    public EmailProperties() {}

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }
}
