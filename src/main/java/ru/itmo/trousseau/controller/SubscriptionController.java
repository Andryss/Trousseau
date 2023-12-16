package ru.itmo.trousseau.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.trousseau.messages.CreateSubscriptionRequest;
import ru.itmo.trousseau.service.CategoryService;
import ru.itmo.trousseau.service.SubscriptionService;

@Controller
@RequiredArgsConstructor
public class SubscriptionController {

    private final CategoryService categoryService;
    private final SubscriptionService subscriptionService;

    @GetMapping("/subscriptions/new")
    public String newSubscriptionPage(CreateSubscriptionRequest createSubscriptionRequest, Model model) {
        model.addAttribute("createSubscriptionRequest", createSubscriptionRequest);
        model.addAttribute("allCategories", categoryService.findAll());
        return "subscription_new";
    }

    @PostMapping("/subscriptions/new")
    public String doCreateSubscription(@Valid CreateSubscriptionRequest createSubscriptionRequest, BindingResult bindingResult, Authentication authentication, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allCategories", categoryService.findAll());
            return "subscription_new";
        }
        subscriptionService.createSubscription(createSubscriptionRequest, authentication.getName());
        return "redirect:/profile";
    }
}
