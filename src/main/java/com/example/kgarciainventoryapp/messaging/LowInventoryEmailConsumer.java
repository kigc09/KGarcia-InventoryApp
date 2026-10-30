package com.example.kgarciainventoryapp.messaging;

import com.example.kgarciainventoryapp.services.LowInventoryEmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LowInventoryEmailConsumer {
    LowInventoryEmailService emailService;

    public LowInventoryEmailConsumer(LowInventoryEmailService es){ emailService = es; }

    @KafkaListener(topics = "${KAFKA_ITEM_STATUS_TOPIC:item-status-events}",
                    groupId = "${KAFKA_EMAIL_GROUP:item-email-notifications}")
    public void receive(LowInventoryEvent event) { emailService.sendMessage(event);}
}
