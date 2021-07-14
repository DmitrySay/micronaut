package com.solbeg;

import com.solbeg.model.Author;
import com.solbeg.model.Book;
import com.solbeg.repository.AuthorR2dbcRepository;
import com.solbeg.repository.BookR2dbcRepository;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.micronaut.http.HttpRequest.GET;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookControllerTest implements TestPropertyProvider {

    static MySQLContainer<?> container;

    @Inject
    R2dbcOperations operations;

    @Inject
    AuthorR2dbcRepository authorRepository;

    @Inject
    BookR2dbcRepository bookRepository;

    @Inject
    @Client("/")
    RxHttpClient client;

    @BeforeAll
    static void setupData(R2dbcOperations operations, AuthorR2dbcRepository authorRepository, BookR2dbcRepository bookRepository) {
        Mono.from(operations.withTransaction(status ->
                Flux.from(authorRepository.save(new Author("Stephen King")))
                        .flatMap((author -> bookRepository.saveAll(Arrays.asList(
                                new Book("The Stand", 1000, author),
                                new Book("The Shining", 400, author)
                        ))))
                        .thenMany(Flux.from(authorRepository.save(new Author("James Patterson"))))
                        .flatMap((author ->
                                bookRepository.save(new Book("Along Came a Spider", 300, author))
                        )).then()
        )).block();
    }

    @AfterAll
    static void cleanup() {
        if (container != null) {
            container.stop();
        }
    }

    @Test
    void testAuthor() {
        //Given
        Author expectedAuthor = new Author(1L, "Stephen King");

        final List list = client.retrieve(GET("/authors"), List.class).blockingSingle();
        log.info("Result: {}", list);
        Assertions.assertEquals(1, list.size());

        final Author authorResult = client.toBlocking().retrieve(GET("/authors/1"), Author.class);
        log.info("Result: {}", authorResult);
        assertThat(expectedAuthor).usingRecursiveComparison().isEqualTo(authorResult);
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
