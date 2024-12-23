package ru.itmo.trousseau.configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "s3")
public class S3Properties {
    @NotBlank
    private String url;
    @NotBlank
    private String accessKey;
    @NotBlank
    private String secretKey;
    @NotBlank
    private String bucket;
}
