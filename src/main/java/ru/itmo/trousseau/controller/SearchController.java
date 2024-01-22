package ru.itmo.trousseau.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itmo.trousseau.messages.SearchRequest;
import ru.itmo.trousseau.service.CategoryService;
import ru.itmo.trousseau.service.ItemService;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final CategoryService categoryService;
    private final ItemService itemService;

    @GetMapping("/search")
    public String searchPage(SearchRequest searchRequest, Model model) {
        model.addAttribute("allCategories", categoryService.findAll());
        model.addAttribute("searchRequest", searchRequest);
        model.addAttribute("result", itemService.findAllWithSearch(searchRequest));
        return "search";
    }
}
