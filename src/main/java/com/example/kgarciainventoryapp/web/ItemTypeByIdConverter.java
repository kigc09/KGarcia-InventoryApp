package com.example.kgarciainventoryapp.web;

import com.example.kgarciainventoryapp.Domain.ItemType;
import org.springframework.core.convert.converter.Converter;

public class ItemTypeByIdConverter implements Converter<String, ItemType> {
    @Override
    public ItemType convert(String source){
        if (source.equals("Item")){
            return ItemType.FOOD_DRINK;
        }
        return ItemType.valueOf(source.toUpperCase());
    }
}
