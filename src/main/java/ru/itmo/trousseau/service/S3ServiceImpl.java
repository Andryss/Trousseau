package ru.itmo.trousseau.service;

import java.io.InputStream;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itmo.trousseau.configuration.S3Properties;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service, InitializingBean, DisposableBean {

    private final S3Properties properties;
    private MinioClient client;

    @Override
    public void afterPropertiesSet() {
        client = MinioClient.builder()
                .endpoint(properties.getUrl())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
        try {
            if (!client.bucketExists(BucketExistsArgs.builder().bucket(properties.getBucket()).build())) {
                client.makeBucket(MakeBucketArgs.builder().bucket(properties.getBucket()).build());
            }
        } catch (Exception e) {
            log.error("Error while creating bucket", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() throws Exception {
        client.close();
    }

    @Override
    public void put(String path, MultipartFile file) {
        try {
            client.putObject(
                    PutObjectArgs.builder()
                            .bucket(properties.getBucket())
                            .object(path)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            log.error("Error while uploading object", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] get(String path) {
        try (InputStream stream = client.getObject(
                GetObjectArgs.builder()
                        .bucket(properties.getBucket())
                        .object(path)
                        .build()
        )) {
            return stream.readAllBytes();
        } catch (Exception e) {
            log.error("Error while reading object", e);
            throw new RuntimeException(e);
        }
    }
}
