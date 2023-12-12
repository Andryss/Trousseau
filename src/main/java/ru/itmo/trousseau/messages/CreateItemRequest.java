package ru.itmo.trousseau.messages;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateItemRequest {
    private String title;
    private MultipartFile photo;
    private String description;
    private String[] categories;
}
