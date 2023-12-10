package ru.itmo.trousseau.object;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchInfo {
    private String query;
    private SearchCategory[] categories;
}
