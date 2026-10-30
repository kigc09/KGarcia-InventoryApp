package com.example.kgarciainventoryapp.messaging;

import com.example.kgarciainventoryapp.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LowInventoryConsumer {

    private static final Logger logger = LoggerFactory.getLogger(LowInventoryConsumer.class);

    @KafkaListener(topics = "${KAFKA_ITEM_STATUS_TOPIC:item-status-events}",
                    groupId = "${KAFKA_LOGGING_GROUP:item-email-notifications} ")
    public void receive(LowInventoryEvent event){
        //emailService.sendMessage(event);
        logger.info(
                "Low Inventory: Item id: {}, Name: {}, Manufacturer: {}, Inventory: {}",
                event.getItemId(),
                event.getName(),
                event.getItemManufacturer(),
                event.getInventory()
        );
    }
}
