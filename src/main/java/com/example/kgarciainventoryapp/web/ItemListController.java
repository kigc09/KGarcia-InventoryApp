package com.example.kgarciainventoryapp.web;

import com.example.kgarciainventoryapp.Domain.ItemType;
import com.example.kgarciainventoryapp.config.ItemProperties;
import com.example.kgarciainventoryapp.services.ItemService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import com.example.kgarciainventoryapp.Domain.ItemDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/list")
public class ItemListController {
    private static final Logger logger = LoggerFactory.getLogger(ItemListController.class);
    private final ItemService itemService;
    private final ItemProperties itemProperties;

    public ItemListController(ItemService is, ItemProperties ip) {
        itemService = is;
        itemProperties = ip;
    }


    @ModelAttribute
    public void addItemTypeToModel(Model model){ model.addAttribute("ItemType", ItemType.values()); }

    @ModelAttribute("pageTitle")
    public String addPageTitle(){return "List Items";}

    /*
    @GetMapping
    public String listItems(Model model){
        model.addAttribute("itemDB", itemDB.getItems());
        return "listItems";
    }
     */

    @GetMapping
    public String listItems(@RequestParam(defaultValue = "") String searchName,
                            @RequestParam(required = false) ItemType filterType,
                            @RequestParam(defaultValue = "0") int page,
                            Model model){
        Pageable pageable = PageRequest.of(page, itemProperties.getPageSize(), Sort.by("name").ascending().and(Sort.by("id")));
        model.addAttribute("itemDB", itemService.searchItems(searchName, filterType, pageable));

        model.addAttribute("searchName", searchName);
        model.addAttribute("filterType", filterType);

        return "listItems";
    }
}
