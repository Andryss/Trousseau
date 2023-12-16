package ru.itmo.trousseau.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.trousseau.controller.validator.CreateItemRequestValidator;
import ru.itmo.trousseau.messages.CreateItemRequest;
import ru.itmo.trousseau.model.CategoryWithGroup;
import ru.itmo.trousseau.model.Dormitory;
import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.model.User;
import ru.itmo.trousseau.service.BookingService;
import ru.itmo.trousseau.service.CategoryService;
import ru.itmo.trousseau.service.DormitoryService;
import ru.itmo.trousseau.service.ItemService;
import ru.itmo.trousseau.service.SavedItemsService;
import ru.itmo.trousseau.service.UserService;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final CreateItemRequestValidator createItemRequestValidator;

    private final ItemService itemService;
    private final BookingService bookingService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final DormitoryService dormitoryService;
    private final SavedItemsService savedItemsService;

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

    @GetMapping("/items/new")
    public String newItemPage(CreateItemRequest createItemRequest, Model model) {
        List<CategoryWithGroup> categories = categoryService.findAll();
        model.addAttribute("createItemRequest", createItemRequest);
        model.addAttribute("allCategories", categories);
        return "item_new";
    }

    @PostMapping("/items/new")
    public String doCreateItem(@Valid CreateItemRequest createItemRequest, BindingResult bindingResult, Authentication authentication, Model model) {
        createItemRequestValidator.validate(createItemRequest, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("allCategories", categoryService.findAll());
            return "item_new";
        }
        itemService.createItem(createItemRequest, authentication.getName());
        return "redirect:/profile";
    }

    @PostMapping("/items/{item_id}:bookItem")
    public String doBookItem(@PathVariable("item_id") long itemId, Authentication authentication) {
        bookingService.bookItem(itemId, authentication.getName());
        return "redirect:/profile";
    }

    @PostMapping("/items/{item_id}:closeItem")
    public String doCloseItem(@PathVariable("item_id") long itemId, Authentication authentication) {
        bookingService.closeItem(itemId, authentication.getName());
        return "redirect:/profile";
    }

    @PostMapping("/items/{item_id}:cancelBooking")
    public String doCancelBooking(@PathVariable("item_id") long itemId, Authentication authentication) {
        bookingService.cancelBooking(itemId, authentication.getName());
        return "redirect:/profile";
    }

    @PostMapping("/items/{item_id}:save")
    public String doAddToSaved(@PathVariable("item_id") long itemId, Authentication authentication) {
        savedItemsService.addToSaved(authentication.getName(), itemId);
        return "redirect:/profile";
    }

    @PostMapping("/items/{item_id}:unsave")
    public String doDeleteFromSaved(@PathVariable("item_id") long itemId, Authentication authentication) {
        savedItemsService.deleteFromSaved(authentication.getName(), itemId);
        return "redirect:/profile";
    }
}
