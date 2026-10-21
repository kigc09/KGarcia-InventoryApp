package com.example.kgarciainventoryapp.api;

import com.example.kgarciainventoryapp.Domain.Image;
import com.example.kgarciainventoryapp.Domain.Item;

import java.util.Base64;

public class ItemRequestMapper {

    private ItemRequestMapper(){}

    public static Item fromCreate(ItemCreateRequestDTO requestDTO){
        Item item = new Item();

        item.setName(requestDTO.getName());
        item.setItemType(requestDTO.getItemType());
        item.setManufacturer(requestDTO.getManufacturer());
        item.setPrice(requestDTO.getPrice());
        item.setInventory(requestDTO.getInventory());
        item.setItemType(requestDTO.getItemType());
        item.setImage(fromImageRequest(requestDTO.getImage()));

        return item;
    }

    public static Image fromImageRequest(ImageRequestDTO requestDTO){
        if (requestDTO == null || requestDTO.getContents() == null || requestDTO.getContents().isBlank()){
            return new Image();
        }
        try {
            byte[] contents = Base64.getDecoder().decode(requestDTO.getContents()); // convert to bytes

            return new Image(requestDTO.getImageName(), requestDTO.getEncoding(), contents);
        }
        catch(IllegalArgumentException ex) {
            return new Image(); // return blank if errors
        }
    }
}