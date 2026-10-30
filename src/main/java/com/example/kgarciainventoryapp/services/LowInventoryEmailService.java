package com.example.kgarciainventoryapp.services;

import com.example.kgarciainventoryapp.config.EmailProperties;
import com.example.kgarciainventoryapp.data.UserRepository;
import com.example.kgarciainventoryapp.messaging.LowInventoryEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class LowInventoryEmailService {
    private static final Logger logger = LoggerFactory.getLogger(LowInventoryEmailService.class);
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final EmailProperties emailProperties;

    public LowInventoryEmailService(JavaMailSender jms, UserRepository ur, EmailProperties ep){
        mailSender = jms;
        userRepository = ur;
        emailProperties = ep;
    }

    public void sendMessage(LowInventoryEvent event){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(emailProperties.getFromAddress());
        message.setTo(emailProperties.getFromAddress());

        message.setSubject("Low Inventory: " + event.getInventory() + "," +
                            "  " + event.getName());
        message.setText("Item ID: " + event.getItemId() + "Item Name: " + event.getName() + "Inventory: " + event.getInventory());

        mailSender.send(message );
    }
}
