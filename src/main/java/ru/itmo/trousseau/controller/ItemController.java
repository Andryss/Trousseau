package ru.itmo.trousseau.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.service.ItemService;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/{item_id}")
    public String itemPage(@PathVariable("item_id") long itemId) {
        Item item = itemService.findById(itemId);
        return "item";
    }
}
