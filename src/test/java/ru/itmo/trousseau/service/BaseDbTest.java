package ru.itmo.trousseau.service;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.itmo.trousseau.configuration.MockBeanConfig;

@SpringBootTest
@AutoConfigureEmbeddedDatabase(
        refresh = AutoConfigureEmbeddedDatabase.RefreshMode.BEFORE_CLASS,
        type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES,
        provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY
)
@Import(MockBeanConfig.class)
public abstract class BaseDbTest {
}
