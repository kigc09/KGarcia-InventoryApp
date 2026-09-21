package com.example.kgarciainventoryapp.web;

import org.springframework.ui.Model;
import com.example.kgarciainventoryapp.Domain.ItemDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/list")
public class ItemListController {
    private static final Logger logger = LoggerFactory.getLogger(ItemListController.class);

    @Autowired
    private ItemDB itemDB;

    @ModelAttribute("pageTitle")
    public String addPageTitle(){return "List Animals";}

    @GetMapping
    public String listItems(Model model){
        model.addAttribute("itemDB", itemDB.getItems());
        return "listItems";
    }
}
