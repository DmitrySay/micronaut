package com.solbeg.integration;

import com.solbeg.model.Author;
import com.solbeg.model.Book;
import com.solbeg.repository.AuthorR2dbcRepository;
import com.solbeg.repository.BookR2dbcRepository;
import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static io.micronaut.http.HttpRequest.GET;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthorR2dbcControllerTest extends AbstractControllerTest {

    @Inject
    AuthorR2dbcRepository authorRepository;

    @Inject
    BookR2dbcRepository bookRepository;

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

}
