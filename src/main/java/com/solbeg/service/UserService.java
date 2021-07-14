package com.solbeg.service;

import com.solbeg.dto.UserDTO;
import com.solbeg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class UserService {
    public static final String USER_NOT_FOUND_MSG = "User with id=[%s] not found";
    private final UserRepository userRepository;

    public Flux<UserDTO> getUsers() {
        return userRepository.findAll()
                .map(user -> new UserDTO(user.getId(), user.getEmail()));
    }

    @Transactional
    public Mono<UserDTO> get(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDTO(user.getId(), user.getEmail()));
    }
}
