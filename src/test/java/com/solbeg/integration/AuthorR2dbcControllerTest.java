package com.solbeg.integration;

import com.solbeg.model.Author;
import com.solbeg.model.Book;
import com.solbeg.model.User;
import com.solbeg.repository.AuthorR2dbcRepository;
import com.solbeg.repository.BookR2dbcRepository;
import com.solbeg.repository.UserR2dbcRepository;
import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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

    @Inject
    UserR2dbcRepository userR2dbcRepository;

    private User fakeUser1 = new User("fake_test1@email.com", "123");

    @BeforeAll
    void setupData(R2dbcOperations operations, AuthorR2dbcRepository authorRepository, BookR2dbcRepository bookRepository) {
        Mono.from(operations.withTransaction(status ->
                Flux.from(authorRepository.save(new Author("Stephen King")))
                        .flatMap((author -> bookRepository.saveAll(Arrays.asList(
                                new Book("The Stand", 1000, author),
                                new Book("The Shining", 400, author)
                        ))))
                        .thenMany(Flux.from(userR2dbcRepository.save(fakeUser1)))
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
        MutableHttpRequest<Object> requestOne =
                GET("/authors")
                        .bearerAuth(getTestToken(fakeUser1.getEmail(), fakeUser1.getPassword()).getAccessToken());

        final List list = client.retrieve(requestOne, List.class).blockingSingle();
        log.info("Result: {}", list);
        Assertions.assertEquals(1, list.size());

        MutableHttpRequest<Object> requestTwo =
                GET("/authors/1")
                        .bearerAuth(getTestToken(fakeUser1.getEmail(), fakeUser1.getPassword()).getAccessToken());

        final Author authorResult = client.toBlocking().retrieve(requestTwo, Author.class);
        log.info("Result: {}", authorResult);
        assertThat(expectedAuthor).usingRecursiveComparison().isEqualTo(authorResult);
    }

    @Disabled //turn of security SecurityRule.IS_ANONYMOUS
    @Test
    void testAuthor2() {
        Author a1 = new Author(1L, "Stephen King");
        Author a2 = new Author(2L, "James Patterson");

        Flowable<Author> retrieve = client.retrieve(GET("/authors"), Author.class);
        Flux<Author> flux = Flux.from(retrieve)
                .doOnNext((item -> {
                    System.out.println("  item is : " + item);
                }));

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext(a1)
//                .expectNext(a2)
                .verifyComplete();
    }

}
