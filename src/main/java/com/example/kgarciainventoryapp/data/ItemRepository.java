package com.example.kgarciainventoryapp.data;

import com.example.kgarciainventoryapp.Domain.Item;
import com.example.kgarciainventoryapp.Domain.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> findByNameContainingIgnoreCase(String name);
    List<Item> findByItemType(ItemType itemType);

    List<Item> findByNameContainingIgnoreCaseAndItemType(String name, ItemType itemType);
}
