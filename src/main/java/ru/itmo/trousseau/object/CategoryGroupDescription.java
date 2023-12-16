package ru.itmo.trousseau.object;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.trousseau.model.Category;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryGroupDescription {
    private String name;
    private String description;
    private List<Category> categories;
}
