package ru.itmo.trousseau.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itmo.trousseau.model.Dormitory;
import ru.itmo.trousseau.model.User;
import ru.itmo.trousseau.service.DormitoryService;
import ru.itmo.trousseau.service.UserService;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final DormitoryService dormitoryService;

    @GetMapping("/profile")
    public String profilePage(Authentication authentication, Model model) {
        User user = userService.findByUsername(authentication.getName());
        Dormitory dormitory = dormitoryService.findById(user.getDormitoryId());
        model.addAttribute("user", user);
        model.addAttribute("dormitory", dormitory);
        return "profile";
    }
}
