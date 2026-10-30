package com.example.kgarciainventoryapp.messaging;

import com.example.kgarciainventoryapp.config.MessagingProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LowInventoryEventProducer {
    private static final Logger logger = LoggerFactory.getLogger(LowInventoryEventProducer.class);
    private final KafkaTemplate<String, LowInventoryEvent> kafkaTemplate;
    private final MessagingProperties properties;

    public LowInventoryEventProducer(KafkaTemplate<String, LowInventoryEvent> template, MessagingProperties properties){
        kafkaTemplate = template;
        this.properties = properties;
    }

    public void send(LowInventoryEvent event){
        kafkaTemplate.send(properties.getLowInventoryTopic(), event.getItemId().toString(),event);
    }
}
