
package com.example.kgarciainventoryapp.security;

import org.springframework.ui.Model;
import com.example.kgarciainventoryapp.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserRegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);
    private final UserService userService;

    public UserRegistrationController(UserService us) { userService = us; }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("userRegistrationForm", new UserRegistrationForm());
        model.addAttribute("pageTitle", "User Registration");
        return "userRegistrationForm";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid UserRegistrationForm userRegistrationForm, Errors errors) {
        if (!userRegistrationForm.getPassword().equals(userRegistrationForm.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "password.mismatch", "The passwords must match.");
        }

        if (errors.hasErrors()){
            return "userRegistrationForm";
        }

        boolean registered = userService.registerNewUser(userRegistrationForm);

        if (!registered){
            errors.rejectValue("username", "username.exists", "That username is already registered.");
            logger.info("User name already exists: {}", userRegistrationForm.getUsername());
            return "userRegistrationForm";
        }

        logger.info("User Created: {}", userRegistrationForm.getUsername());
        return "redirect:/login";
    }
}