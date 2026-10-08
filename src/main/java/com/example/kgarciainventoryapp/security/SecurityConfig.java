package com.example.kgarciainventoryapp.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/error", "/style.css", "/Images/**").permitAll()
                        .requestMatchers("/list", "/view/current/{id}", "/user/register").permitAll()
                        .requestMatchers("/register", "/register/**", "/view/current/{id}/edit",
                                "/view/current/{id}/delete").hasAnyRole( "ADMIN", "MNGR", "ASSOC")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/list", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/list")
                        .permitAll())
                .build();
    }
}
