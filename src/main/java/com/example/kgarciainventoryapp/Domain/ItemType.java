package com.example.kgarciainventoryapp.Domain;

public enum ItemType {
    FOOD_DRINK,
    APPAREL,
    ACCESSORY,
    BOOK,
    SCHOOL_MATERIAL;

    @Override
    public String toString(){
        return switch(this.ordinal()){
            case 0 -> "Food & Drink";
            case 1 -> "Apparel";
            case 2 -> "Accessory";
            case 3 -> "Book";
            case 4 -> "School Material";
            default -> "Other" ;
        };
    }
}
