package com.example.kgarciainventoryapp.api;

import com.example.kgarciainventoryapp.Domain.Item;
import com.example.kgarciainventoryapp.Domain.ItemType;

public class ItemResponseDTO {
    private String id;
    private String name;
    private String manufacturer;
    private double price;
    private int inventory;
    private ItemType itemType;
    private ImageResponseDTO image;

    public ItemResponseDTO(){}

    public static ItemResponseDTO from(Item item){
        ItemResponseDTO responseDTO = new ItemResponseDTO();

        responseDTO.setId(item.getId());
        responseDTO.setName(item.getName());
        responseDTO.setManufacturer(item.getManufacturer());
        responseDTO.setPrice(item.getPrice());
        responseDTO.setInventory(item.getInventory());
        responseDTO.setItemType(item.getItemType());
        responseDTO.setImage(ImageResponseDTO.from(item.getImage()));

        return responseDTO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public ImageResponseDTO getImage() {
        return image;
    }

    public void setImage(ImageResponseDTO image) {
        this.image = image;
    }
}
