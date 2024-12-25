package ru.itmo.trousseau.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Secured("VIEW_HOME_PAGE")
    @GetMapping("/")
    public String welcomePage() {
        return "redirect:/home";
    }

    @Secured("VIEW_HOME_PAGE")
    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
}
