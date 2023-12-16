package ru.itmo.trousseau.messages;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateSubscriptionRequest {
    @NotEmpty
    @Size(max = 64)
    private String name;

    @NotEmpty
    @Size(min = 1, max = 5)
    private String[] categories;
}
