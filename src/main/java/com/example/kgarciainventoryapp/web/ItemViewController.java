package com.example.kgarciainventoryapp.web;

import com.example.kgarciainventoryapp.Domain.Item;
import com.example.kgarciainventoryapp.Domain.ItemDB;
import com.example.kgarciainventoryapp.Domain.ItemType;
import com.example.kgarciainventoryapp.services.ItemService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/view")
public class ItemViewController {
    private static final Logger logger = LoggerFactory.getLogger(ItemViewController.class);
    private final ItemService itemService;

    public ItemViewController(ItemService is){ itemService = is;}

    @Autowired
    private ItemDB itemDB;

    @GetMapping("/current/{id}")
    public String viewItems(@PathVariable String id, Model model){
        /*Item current = itemDB.getItems().get(id);
        if(current == null){
            logger.debug("Item with id: {} not found", id);
            return "redirect:/list";
        }
        model.addAttribute("current", current);
        model.addAttribute("pageTitle", current.getName());
        return "viewItems";*/
        Optional<Item> item = itemService.getItemById(id);
        if(item.isEmpty()){
            logger.debug("Item with id: {} not found to view", id);
            return "redirect:/list";
        }
        Item current = item.get();

        model.addAttribute("current", current);
        model.addAttribute("pageTitle", current.getName());
        return "viewItems";
    }


    @GetMapping("/current/{id}/edit")
    public String viewEditItemForm(@PathVariable String id, Model model) {
        Optional<Item> item = itemService.getItemById(id);
        if (item.isEmpty()) {
            logger.debug("Item with id: {} not found to edit", id);
            // not return the view here and maybe redirect?
            return "redirect:/list";
        }
        // use .get() to get the domain out of the optional
        Item current = item.get();

        model.addAttribute("ItemType", ItemType.values());
        model.addAttribute("item", current);
        model.addAttribute("pageTitle", current.getName());

        return "itemModifyForm";
    }

    @PostMapping("/current/{id}/edit")
    public String modifyItem(@PathVariable String id, @Valid Item item, Errors errors, Model model) {
        logger.debug("Item received {} for modification", item);

        if (errors.hasErrors()) {
            model.addAttribute("ItemType", ItemType.values());
            model.addAttribute("pageTitle", item.getName());
            return "itemModifyForm";
        }

        itemService.updateItem(id, item);
        return "redirect:/view/current/" + id;
    }

    @PostMapping("/current/{id}/delete")
    public String deleteItem(@PathVariable String id) {

        itemService.deleteItemById(id);

        return "redirect:/list";
    }
}
