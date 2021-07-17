package com.solbeg.service;

import com.solbeg.dto.UserDTO;
import com.solbeg.model.User;
import com.solbeg.repository.UserRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import javax.inject.Inject;
import java.util.List;

@MicronautTest
class UserServiceTest {

    @Inject
    private UserService userService;

    @Inject
    private UserRepository userRepository;

    @Test
    void getFluxUsers() {
        Flux<UserDTO> users = userService.getUsers();
    }

    @Test
    void getUsers() {
        List<User> users = userRepository.findAll();
        users.forEach(System.out::println);
    }

    @Test
    void get() {
    }
}