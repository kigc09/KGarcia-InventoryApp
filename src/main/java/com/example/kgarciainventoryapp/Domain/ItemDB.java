package com.example.kgarciainventoryapp.Domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Scope("singleton")
public class ItemDB {
    private Map<UUID, Item> items = new HashMap<>();

    public Map<UUID, Item> getItems(){
        return items;
    }

    public void addItem(Item item){
        items.put(item.getId(), item);
    }
}
