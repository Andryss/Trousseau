package ru.itmo.trousseau.object;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.trousseau.model.CategoryWithGroup;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDescription {
    private String name;
    private List<CategoryWithGroup> categories;
    private Timestamp creationDatetime;
}
