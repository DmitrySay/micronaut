package com.solbeg.service;

import com.solbeg.dto.UserDTO;
import com.solbeg.mapper.UserMapper;
import com.solbeg.model.User;
import com.solbeg.repository.UserR2dbcRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest
class UserServiceTest {

    UserR2dbcRepository userR2dbcRepository;
    UserService userService;
    UserMapper userMapper;

    @BeforeAll
    void setup() {
        userR2dbcRepository = mock(UserR2dbcRepository.class);
        userMapper = mock(UserMapper.class);
        userService = new UserService(userR2dbcRepository, userMapper);
    }

    @Test
    void getFluxUsers() {
        //Given
        User user1 = new User(900L, "fake3@email.com", "123");
        User user2 = new User(901L, "fake4@email.com", "456");
        UserDTO actualUserDTO1 = new UserDTO(900L, "fake3@email.com");
        UserDTO actualUserDTO2 = new UserDTO(901L, "fake4@email.com");
        List<User> usersDB = Arrays.asList(user1, user2);

        when(userR2dbcRepository.findAll()).thenReturn(Flux.fromIterable(usersDB));
        when(userMapper.toDTO(user1)).thenReturn(actualUserDTO1);
        when(userMapper.toDTO(user2)).thenReturn(actualUserDTO2);

        Flux<UserDTO> users = userService.getUsers();
        users.toStream().forEach(u -> log.info("Result: {}", u));
        UserDTO userDTO1 = users.blockFirst();
        UserDTO userDTO2 = users.blockLast();
        //then
        assertThat(actualUserDTO1).usingRecursiveComparison().isEqualTo(userDTO1);
        assertThat(actualUserDTO2).usingRecursiveComparison().isEqualTo(userDTO2);
    }

}