package com.example.kgarciainventoryapp.services;

import com.example.kgarciainventoryapp.Domain.User;
import com.example.kgarciainventoryapp.data.ItemRepository;
import com.example.kgarciainventoryapp.Domain.Item;
import com.example.kgarciainventoryapp.Domain.ItemType;
import com.example.kgarciainventoryapp.web.RegistrationController;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);
    private final ItemRepository itemRepo;

    public ItemService(ItemRepository ir){ itemRepo = ir;}

    @Transactional
    public Item registerNewItem(Item item, User user){
        if(item.hasImage()){
            String imageName = item.getImage().getImageName();
            imageName = item.getName() + imageName.substring(imageName.lastIndexOf("."));
            item.getImage().setImageName(imageName);
        }

        item = itemRepo.save(item);

        logger.info("Item Registered {}", item);
        return item;
    }

    public Optional<Item> getItemById(String  id){ return itemRepo.findById(id); }

    public Map<String, Item> getAllItems() {
        Map<String, Item> db = new HashMap<>();
        itemRepo.findAll().forEach(item -> db.put(item.getId(), item));
        return db;
    }


    public Map<String, Item> searchItems(String searchName, ItemType filterType){
        Map<String, Item> db = new HashMap<>();

        boolean hasName = searchName != null && !searchName.isBlank();
        boolean hasType = filterType != null;

        if (hasName && hasType){
            itemRepo.findByNameContainingIgnoreCaseAndItemType(searchName, filterType).forEach(item -> db.put(item.getId(), item));
        }
        else if (hasName){
            itemRepo.findByNameContainingIgnoreCase(searchName).forEach(item -> db.put(item.getId(), item));
        }
        else if (hasType){
            itemRepo.findByItemType(filterType).forEach(item -> db.put(item.getId(), item));
        }
        else {
            return getAllItems();
        }
        return db;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasRole('MNGR')")
    public boolean updateItem(String id, Item item){
        if(!itemRepo.existsById(id)){
            logger.info("Error in saving item, ID: {} doesn't exist.", id);
            return false;
        }
        Item lookup = itemRepo.findById(id).get();

        if(lookup.hasImage() && !item.hasImage()) {
            item.setImage(lookup.getImage());
        }

        item.setId(id);
        itemRepo.save(item);

        logger.info("Item was saved at ID: {}.", id);
        return true;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasRole('MNGR') or hasRole('ASSOC')")
    public boolean updateInventoryLevel(String id, int inventory){
        if(!itemRepo.existsById(id)){
            logger.info("Error in updating, ID: {} doesn't exist.", id);
            return false;
        }

        itemRepo.updateInventoryById(id, inventory);

        logger.info("Item was updated at ID: {}", id);

        return true;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasRole('MNGR')")
    public boolean deleteItemById(String id){
        if(!itemRepo.existsById(id)){
            logger.info("Error, no item with ID: {}", id);
            return false;
        }
        itemRepo.deleteById(id);
        logger.info("Item deleted with ID: {}", id);
        return true;
    }
}
