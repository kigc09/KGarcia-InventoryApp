package com.example.kgarciainventoryapp.web;

import com.example.kgarciainventoryapp.Domain.Item;
import com.example.kgarciainventoryapp.Domain.ItemDB;
import com.example.kgarciainventoryapp.Domain.ItemType;
import com.example.kgarciainventoryapp.Domain.User;
import com.example.kgarciainventoryapp.services.ItemService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private final ItemService itemService;

    public RegistrationController(ItemService is){ itemService = is; }

    @ModelAttribute("pageTitle")
    public String addPageTitle(){ return "Enter Item"; }
    @ModelAttribute
    public void addItemTypeToModel(Model model){
        model.addAttribute("ItemType", ItemType.values());
    }

    @ModelAttribute()
    public Item item(){ return new Item(); }

    @GetMapping
    public String registerForm(){
        return "itemRegistrationForm";
    }

    @PostMapping
    public String processItemRegister(@Valid Item item, @AuthenticationPrincipal User user, Errors errors, RedirectAttributes redirectAttributes){
        logger.debug("Item received {}", item);

        /*if (item.hasImage()) {
            String imageName = item.getImage().getImageName();
            imageName = item.getName() + imageName.substring(imageName.lastIndexOf("."));
            item.getImage().setImageName(imageName);
        }

        itemDB.addItem(item);
        logger.info("Item Registered {}", item);
        return "redirect:/view/current/" + item.getId();

        if(errors.hasErrors()){
            return "itemRegistrationForm";
        }*/

        Item addedItem = itemService.registerNewItem(item, user);
        redirectAttributes.addFlashAttribute("succesMessage", "New item added!");
        return "redirect:/view/current/" + addedItem.getId();
    }
}
