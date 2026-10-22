package com.example.kgarciainventoryapp.api;

import com.example.kgarciainventoryapp.Domain.Item;
import com.example.kgarciainventoryapp.Domain.ItemType;
import com.example.kgarciainventoryapp.Domain.User;
import com.example.kgarciainventoryapp.config.ItemProperties;
import com.example.kgarciainventoryapp.services.ItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/items", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemRestController {
    private final ItemService itemService;
    private final ItemProperties itemProperties;

    public ItemRestController(ItemService is, ItemProperties ip){
        itemService = is;
        itemProperties = ip;
    }

    @GetMapping
    public Page<ItemResponseDTO> getItems(@RequestParam(defaultValue = "0")int page){
        Pageable pageable = PageRequest.of(page, itemProperties.getPageSize(),
                Sort.by("name").ascending().and(Sort.by("id")));

        return itemService.getAllItems(pageable).map(ItemResponseDTO::from);
    }

    @GetMapping("/search")
    public Page<ItemResponseDTO> searchItems(@RequestParam(defaultValue = "")String name,
                                             @RequestParam(required = false) ItemType itemType,
                                             @RequestParam(defaultValue = "0")int page) {
        Pageable pageable = PageRequest.of(page, itemProperties.getPageSize(),
                Sort.by("name").ascending().and(Sort.by("id")));

        return itemService.searchItems(name, itemType, pageable).map(ItemResponseDTO::from);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ItemResponseDTO> getItem(@PathVariable String id,
                                                   @AuthenticationPrincipal Jwt jwt) {
        Optional<Item> item = itemService.getItemById(id);

        //Convert String to pass through JWT

        if(item.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ItemResponseDTO responseDTO = ItemResponseDTO.from(item.get());

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping(consumes = "application/json")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ItemResponseDTO> createItem(@Valid @RequestBody ItemCreateRequestDTO requestDTO,
                                                      @AuthenticationPrincipal Jwt jwt){
        Item item = ItemRequestMapper.fromCreate(requestDTO);

        String username = jwt.getSubject();

        Item savedItem = itemService.registerNewItem(item, username);

        ItemResponseDTO response = ItemResponseDTO.from(savedItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ItemResponseDTO> patchItem(@PathVariable String id,
                                                     @RequestBody ItemPatchRequestDTO requestDTO){
        Optional<Item> updatedItem = itemService.patchUpdate(id, requestDTO);

        if(updatedItem.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        ItemResponseDTO responseDTO = ItemResponseDTO.from(updatedItem.get());

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deleteItem(@PathVariable String id){
        boolean deleted = itemService.deleteItemById(id);

        if(!deleted){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}