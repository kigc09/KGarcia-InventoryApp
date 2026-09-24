package com.example.kgarciainventoryapp.Domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class Item {
    private UUID id = UUID.randomUUID();
    @NotBlank(message = "A name is required")
    private String name;
    @NotBlank(message = "A manufacturer is required")
    private String manufacturer;
    @NotNull(message = "Price is required")
    private double price;
    @NotNull(message = "Inventory amount is required")
    private int inventory;
    @NotNull(message = "Item type is required")
    private ItemType itemType;
    private Image image;

    public Item(){
    }

    public Item(UUID id, String name, String manufacturer, double price, int inventory, ItemType itemType, Image image){
         this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.inventory = inventory;
        this.itemType = itemType;
        this.image = image;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Image getImage() {return image;}

    public void setImage(Image image){this.image = image;}

    public boolean hasImage() { return image != null && image.getContents().length > 0; }

    @Override
    public String toString(){
        return "Item{"+
                "id= " + id +
                "name= " + name +
                "manufacturer= " + manufacturer +
                "price= " + price +
                "inventory= " + inventory +
                "itemType= " + itemType +
                "image= " + image +
                "}";
    }
}
