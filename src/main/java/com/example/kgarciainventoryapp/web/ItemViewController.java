package com.example.kgarciainventoryapp.web;

import com.example.kgarciainventoryapp.Domain.Item;
import com.example.kgarciainventoryapp.Domain.ItemDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/view")
public class ItemViewController {
    private static final Logger logger = LoggerFactory.getLogger(ItemViewController.class);
    @Autowired
    private ItemDB itemDB;

    @GetMapping("/current/{id}")
    public String viewItems(@PathVariable UUID id, Model model){
        Item current = itemDB.getItems().get(id);
        if(current == null){
            logger.debug("Item with id: {} not found", id);
            return "redirect:/list";
        }
        model.addAttribute("current", current);
        model.addAttribute("pageTitle", current.getName());
        return "viewItems";
    }
}
