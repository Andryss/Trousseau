package ru.itmo.trousseau.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.trousseau.model.CategoryWithGroup;
import ru.itmo.trousseau.model.Dormitory;
import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.model.User;
import ru.itmo.trousseau.service.CategoryService;
import ru.itmo.trousseau.service.DormitoryService;
import ru.itmo.trousseau.service.ItemService;
import ru.itmo.trousseau.service.UserService;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final DormitoryService dormitoryService;

    @GetMapping("/items/{item_id}")
    public String itemPage(@PathVariable("item_id") long itemId, Model model) {
        Item item = itemService.findById(itemId);
        List<CategoryWithGroup> categories = categoryService.findAllByItemId(item.getId());
        User owner = userService.findById(item.getUserId());
        Dormitory dormitory = dormitoryService.findById(owner.getDormitoryId());
        model.addAttribute("item", item);
        model.addAttribute("categories", categories);
        model.addAttribute("owner", owner);
        model.addAttribute("dormitory", dormitory);
        return "item";
    }
}
