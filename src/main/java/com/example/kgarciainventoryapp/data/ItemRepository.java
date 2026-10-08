package com.example.kgarciainventoryapp.data;

import com.example.kgarciainventoryapp.Domain.Item;
import com.example.kgarciainventoryapp.Domain.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> findByNameContainingIgnoreCase(String name);
    List<Item> findByItemType(ItemType itemType);
    List<Item> findByNameContainingIgnoreCaseAndItemType(String name, ItemType itemType);

    @Modifying
    @Transactional
    @Query("UPDATE Item i SET i.inventory = :inventoryLevel WHERE i.id = :id")
    int updateInventoryById(@Param("id") String id, @Param("inventoryLevel") int inventory);
}
