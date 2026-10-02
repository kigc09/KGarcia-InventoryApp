package com.example.kgarciainventoryapp.web;

import com.example.kgarciainventoryapp.Domain.ItemType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ItemTypeByIdConverter implements Converter<String, ItemType> {
    @Override
    public ItemType convert(String source){
        if (source.equals("Food & Drink")){
            return ItemType.FOOD_DRINK;
        }
        if (source.equals("School Material")){
            return ItemType.SCHOOL_MATERIAL;
        }
        return ItemType.valueOf(source.toUpperCase());
    }
}
