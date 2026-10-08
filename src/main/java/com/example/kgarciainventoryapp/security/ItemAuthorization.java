package com.example.kgarciainventoryapp.security;

import com.example.kgarciainventoryapp.data.ItemRepository;
import org.springframework.stereotype.Component;

@Component
public class ItemAuthorization {
    private final ItemRepository itemRepo;

    public ItemAuthorization(ItemRepository ir) { itemRepo = ir; }
}
