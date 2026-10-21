package com.example.kgarciainventoryapp.api;

import com.example.kgarciainventoryapp.Domain.ItemType;

public class ItemPatchRequestDTO {
    private String name;
    private String manufacturer;
    private double price;
    private int inventory;
    private ItemType itemType;
    private ImageRequestDTO image;

    public ItemPatchRequestDTO() {}

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

    public ImageRequestDTO getImage() {
        return image;
    }

    public void setImage(ImageRequestDTO image) {
        this.image = image;
    }

}
