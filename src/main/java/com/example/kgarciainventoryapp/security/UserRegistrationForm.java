package com.example.kgarciainventoryapp.security;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class UserRegistrationForm {

    private UUID id;

    @NotBlank(message="")
    @Size(min=5, max=50, message="Username must be between 5 and 50 character.")
    private String username;
    @NotBlank(message = "A password is required.")
    @Size(min=8, message="Password must be at least 8 characters long.")
    private String password;
    @NotBlank(message = "Please confirm your password.")
    private String confirmPassword;
    @NotBlank(message = "Your full name is required.")
    private String fullname;

    public UserRegistrationForm(){
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }

    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public String getFullname() { return fullname; }

    public void setFullname(String fullname) { this.fullname = fullname; }
}
