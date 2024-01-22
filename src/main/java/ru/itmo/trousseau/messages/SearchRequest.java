package ru.itmo.trousseau.messages;

import lombok.Data;

@Data
public class SearchRequest {
    private String query;
    private String[] categories;
}
