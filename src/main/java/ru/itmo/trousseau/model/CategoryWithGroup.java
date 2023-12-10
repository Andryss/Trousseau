package ru.itmo.trousseau.model;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CategoryWithGroup {
    private long id;
    private String name;
    private String description;
    @Column(name = "group_id")
    private long groupId;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "group_description")
    private String groupDescription;
}
