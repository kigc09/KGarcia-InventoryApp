package com.example.kgarciainventoryapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "item")
public class ItemProperties {

    private int pageSize = 9;
    private int lowInventory = 3;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize){
        this.pageSize = pageSize;
    }

    public int getLowInventory(){
        return lowInventory;
    }

    public void setLowInventory(int lowInventory){
        this.lowInventory = lowInventory;
    }
}
