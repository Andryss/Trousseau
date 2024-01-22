package ru.itmo.trousseau.messages;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateItemRequest {
    @NotEmpty
    @Size(min = 10, max = 64)
    private String title;

    @NotNull
    private MultipartFile photo;

    @NotEmpty
    @Size(min = 20, max = 1024)
    private String description;

    @NotEmpty
    @Size(min = 1, max = 5)
    private String[] categories;
}
