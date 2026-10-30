package com.example.kgarciainventoryapp.messaging;

import com.example.kgarciainventoryapp.Domain.Item;

import java.util.UUID;

public class LowInventoryEvent {
    private String eventId;
    private String itemId;
    private String name;
    private String itemManufacturer;
    private int inventory;

    public LowInventoryEvent() {}

    public static LowInventoryEvent fromItem(Item item){
        LowInventoryEvent event = new LowInventoryEvent();

        event.setEventId(UUID.randomUUID().toString());
        event.setItemId(item.getId());
        event.setName(item.getName());
        event.setItemManufacturer(item.getManufacturer());
        event.setInventory(item.getInventory());

        return event;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getItemManufacturer() {
        return itemManufacturer;
    }

    public void setItemManufacturer(String itemManufacturer) {
        this.itemManufacturer = itemManufacturer;
    }


}
