package ru.itmo.trousseau.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itmo.trousseau.model.Dormitory;
import ru.itmo.trousseau.model.Item;
import ru.itmo.trousseau.model.User;
import ru.itmo.trousseau.object.SubscriptionDescription;
import ru.itmo.trousseau.service.BookingService;
import ru.itmo.trousseau.service.DormitoryService;
import ru.itmo.trousseau.service.ItemService;
import ru.itmo.trousseau.service.SavedItemsService;
import ru.itmo.trousseau.service.SubscriptionService;
import ru.itmo.trousseau.service.UserService;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final DormitoryService dormitoryService;
    private final ItemService itemService;
    private final BookingService bookingService;
    private final SavedItemsService savedItemsService;
    private final SubscriptionService subscriptionService;

    @GetMapping("/profile")
    public String profilePage(Authentication authentication, Model model) {
        User user = userService.findByUsername(authentication.getName());
        Dormitory dormitory = dormitoryService.findById(user.getDormitoryId());
        List<Item> bookings = bookingService.findAllBookedBy(user.getId());
        List<Item> items = itemService.findAllOwnedBy(user.getId());
        List<Item> savedItems = savedItemsService.findAllSavedBy(user.getId());
        List<SubscriptionDescription> subscriptions = subscriptionService.findAllOwnedBy(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("dormitory", dormitory);
        model.addAttribute("bookings", bookings);
        model.addAttribute("items", items);
        model.addAttribute("savedItems", savedItems);
        model.addAttribute("subscriptions", subscriptions);
        return "profile";
    }
}
