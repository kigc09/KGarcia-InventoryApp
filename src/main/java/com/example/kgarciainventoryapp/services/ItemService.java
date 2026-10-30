package com.example.kgarciainventoryapp.services;

import com.example.kgarciainventoryapp.Domain.User;
import com.example.kgarciainventoryapp.api.ItemPatchRequestDTO;
import com.example.kgarciainventoryapp.api.ItemRequestMapper;
import com.example.kgarciainventoryapp.config.ItemProperties;
import com.example.kgarciainventoryapp.data.ItemRepository;
import com.example.kgarciainventoryapp.Domain.Item;
import com.example.kgarciainventoryapp.Domain.ItemType;
import com.example.kgarciainventoryapp.data.UserRepository;
import com.example.kgarciainventoryapp.messaging.LowInventoryEvent;
import com.example.kgarciainventoryapp.messaging.LowInventoryEventProducer;
import com.example.kgarciainventoryapp.web.RegistrationController;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);
    private final ItemRepository itemRepo;
    private final UserRepository userRepo;
    private final LowInventoryEventProducer lowInvEventProducer;
    private ItemProperties itemProperties;

    public ItemService(ItemRepository ir, UserRepository ur,LowInventoryEventProducer lowInvEventProducer, ItemProperties itemProperties){
        this.itemRepo = ir;
        this.userRepo = ur;
        this.lowInvEventProducer = lowInvEventProducer;
        this.itemProperties = itemProperties;
    }

    private void lowInventory(Item item){
        logger.info("Checking inventory: Item Id: {}, Name: {}, Inventory: {}",
                item.getId(), item.getName(), item.getInventory());
        logger.info("Current Inventory: {}",item.getInventory());
        logger.info("Low Inventory: {}", item.getLowInventory());

        if (item.getInventory() <= item.getLowInventory()){
            logger.info("It's low");
            LowInventoryEvent event = LowInventoryEvent.fromItem(item);

            lowInvEventProducer.send(event);
        }
    }

    @Transactional
    public Item registerNewItem(Item item, String username){

        item.setId(null);

        if(item.hasImage()){
            String imageName = item.getImage().getImageName();
            imageName = item.getName() + imageName.substring(imageName.lastIndexOf("."));
            item.getImage().setImageName(imageName);
        }

        item = itemRepo.save(item);
        lowInventory(item);

        logger.info("Item Registered {}", item);
        return item;
    }

    public Optional<Item> getItemById(String  id){ return itemRepo.findById(id); }

    public Page<Item> getAllItems(Pageable pageable) {
        return itemRepo.findAll(pageable);

    }


    public Page<Item> searchItems(String searchName, ItemType filterType, Pageable pageable){
        boolean hasName = searchName != null && !searchName.isBlank();
        boolean hasType = filterType != null;

        if (hasName && hasType){
            return itemRepo.findByNameContainingIgnoreCaseAndItemType(searchName, filterType, pageable);
        }
        else if (hasName){
            return itemRepo.findByNameContainingIgnoreCase(searchName, pageable);
        }
        else if (hasType){
            return itemRepo.findByItemType(filterType, pageable);
        }
        else {
            return getAllItems(pageable);
        }
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
        item.setLowInventory(lookup.getLowInventory());
        Item updatedItem = itemRepo.save(item);
        lowInventory(updatedItem);

        logger.info("Item was saved at ID: {}.", id);
        return true;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasRole('MNGR') or hasRole('ASSOC')")
    public boolean updateInventoryLevel(String id, int inventory){
        Optional<Item> lookupItem = itemRepo.findById(id);

        if(lookupItem.isEmpty()){
            return false;
        }

        Item item = lookupItem.get();
        item.setInventory(inventory);

        Item updatedItem = itemRepo.save(item);
        lowInventory(updatedItem);


        logger.info("Item was updated at ID: {}", id);

        return true;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasRole('MNGR')")
    public Optional<Item> patchUpdate(String id, ItemPatchRequestDTO requestDTO){
        Optional<Item> lookupItem = itemRepo.findById(id);

        if(lookupItem.isEmpty()){
            return Optional.empty();
        }

        Item item = lookupItem.get();

        if (requestDTO.getName() != null){
            item.setName(requestDTO.getName());
        }
        if (requestDTO.getManufacturer() != null){
            item.setManufacturer(requestDTO.getManufacturer());
        }

        if (requestDTO.getPrice() != null){
            item.setPrice(requestDTO.getPrice());
        }
        if (requestDTO.getInventory() != null){
            item.setInventory(requestDTO.getInventory());
        }
        if (requestDTO.getItemType() != null){
            item.setItemType(requestDTO.getItemType());
        }
        if (requestDTO.getImage() != null){
            item.setImage(ItemRequestMapper.fromImageRequest(requestDTO.getImage()));
        }

        Item updateItem = itemRepo.save(item);
        lowInventory(updateItem);

        return Optional.of(updateItem);
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
