package ru.itmo.trousseau.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.itmo.trousseau.messages.SearchRequest;
import ru.itmo.trousseau.service.ItemService;
import ru.itmo.trousseau.service.SearchService;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    private final ItemService itemService;

    @GetMapping("/search")
    public String searchPage(@ModelAttribute SearchRequest search, Model model) {
        model.addAttribute("searchInfo", searchService.createEmpty());
        model.addAttribute("result", itemService.findAllWithSearch(search));
        return "search";
    }
}
