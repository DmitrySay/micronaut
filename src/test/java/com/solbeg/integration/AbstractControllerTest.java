package com.solbeg.integration;

import io.micronaut.core.util.CollectionUtils;
import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.inject.Inject;
import java.util.Map;

@Slf4j
@MicronautTest
public class AbstractControllerTest implements TestPropertyProvider {

    static MySQLContainer<?> container;

    @Inject
    R2dbcOperations operations;

    @Inject
    @Client("/")
    RxHttpClient client;

    @AfterAll
    static void cleanup() {
        if (container != null) {
            container.stop();
        }
    }

    @Override
    public Map<String, String> getProperties() {
        container = new MySQLContainer<>(DockerImageName.parse("mysql").withTag("5"));
        container.start();
        return CollectionUtils.mapOf(
                "datasources.default.url", container.getJdbcUrl(),
                "datasources.default.username", container.getUsername(),
                "datasources.default.password", container.getPassword(),
                "datasources.default.database", container.getDatabaseName(),
                "datasources.default.driverClassName", container.getDriverClassName(),
                "r2dbc.datasources.default.host", container.getHost(),
                "r2dbc.datasources.default.port", container.getFirstMappedPort(),
                "r2dbc.datasources.default.driver", "mysql",
                "r2dbc.datasources.default.username", container.getUsername(),
                "r2dbc.datasources.default.password", container.getPassword(),
                "r2dbc.datasources.default.database", container.getDatabaseName()
        );
    }
}
