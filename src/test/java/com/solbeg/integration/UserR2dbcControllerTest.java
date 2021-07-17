package com.solbeg.integration;

import com.solbeg.model.User;
import com.solbeg.repository.UserR2dbcRepository;
import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static io.micronaut.http.HttpRequest.GET;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserR2dbcControllerTest extends AbstractControllerTest {
    @Inject
    UserR2dbcRepository userR2dbcRepository;

    @BeforeAll
    static void setupData(R2dbcOperations operations, UserR2dbcRepository userR2dbcRepository) {
        Mono.from(operations.withTransaction(
                status ->
                        userR2dbcRepository.saveAll(
                                Arrays.asList(
                                        new User("fake_test1@email.com", "123"),
                                        new User("fake_test2@email.com", "456")
                                )
                        ).then()
                )
        ).block();
    }


    @Test
    void getAll() {
        final List users = client.retrieve(GET("/users"), List.class).blockingSingle();
        assertEquals(2, users.size());
    }
}