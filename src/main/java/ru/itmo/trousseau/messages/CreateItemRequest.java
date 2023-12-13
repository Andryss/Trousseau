package ru.itmo.trousseau.messages;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateItemRequest {
    @NotNull
    @Size(min = 10, max = 64)
    private String title;
    private MultipartFile photo;
    @NotNull
    @Size(min = 20, max = 1024)
    private String description;
    @Size(max = 5)
    private String[] categories;
}
