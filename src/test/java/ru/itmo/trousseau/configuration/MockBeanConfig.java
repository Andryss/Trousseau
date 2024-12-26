package ru.itmo.trousseau.configuration;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import ru.itmo.trousseau.listener.LinkUserRolesListener;
import ru.itmo.trousseau.listener.UploadPhotosToS3Listener;
import ru.itmo.trousseau.service.S3Service;

@Configuration
public class MockBeanConfig {

    @MockBean
    public LinkUserRolesListener linkUserRolesListener;

    @MockBean
    public UploadPhotosToS3Listener uploadPhotosToS3Listener;

    @MockBean
    public S3Service s3Service;

}
