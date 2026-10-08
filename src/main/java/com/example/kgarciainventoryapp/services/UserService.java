package com.example.kgarciainventoryapp.services;

import com.example.kgarciainventoryapp.data.UserRepository;
import com.example.kgarciainventoryapp.Domain.User;
import com.example.kgarciainventoryapp.security.UserRegistrationForm;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository ur, PasswordEncoder pe){
        userRepo = ur;
        passwordEncoder = pe;
    }

    @Override
    public @NullMarked UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        System.out.println("Username: " + user.getUsername());
        System.out.println("Authorities: " + user.getAuthorities());
        System.out.println("Role: " + user.getRole());

        return user;
    }

    public void createUserIfMissing(User user){
        if(!userRepo.existsByUsername(user.getUsername())){
            logger.info("User created: {}", user.getUsername());
            userRepo.save(user);
        }
        else{
            logger.info("User already exists: {}", user.getUsername());
        }
    }

    @Transactional
    public boolean registerNewUser(UserRegistrationForm form){

        // We check for unique username
        String username = form.getUsername().trim();
        if(userRepo.existsByUsername(username)){
            return false;
        }

        // Encode password
        String encodedPassword = passwordEncoder.encode(form.getPassword());

        // Create user from form
        User newUser = new User(username, encodedPassword, form.getFullname(), "ROLE_USER");

        userRepo.save(newUser);

        return true;
    }
}
