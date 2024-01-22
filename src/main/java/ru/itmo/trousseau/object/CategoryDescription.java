package ru.itmo.trousseau.object;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDescription {
    private String name;
    private String description;
    private String groupName;
    private String groupDescription;
}
