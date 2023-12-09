package ru.itmo.trousseau.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String welcomePage() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
}
