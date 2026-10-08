package com.example.kgarciainventoryapp;

import com.example.kgarciainventoryapp.Domain.User;
import com.example.kgarciainventoryapp.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class KGarciaInventoryAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(KGarciaInventoryAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(UserService userService, PasswordEncoder passwordEncoder){
        return args -> {
            userService.createUserIfMissing(new User("uTest", passwordEncoder.encode("user123"), "USER TEST", "ROLE_ADMIN"));
            userService.createUserIfMissing(new User("mTest", passwordEncoder.encode("mngr123"), "MNGR TEST", "ROLE_MNGR"));
            userService.createUserIfMissing(new User("aTest", passwordEncoder.encode("assoc123"), "ASSOC TEST", "ROLE_ASSOC"));
            userService.createUserIfMissing(new User("uUser", passwordEncoder.encode("test123"), "USER USER", "ROLE_USER"));
        };
    }
}
