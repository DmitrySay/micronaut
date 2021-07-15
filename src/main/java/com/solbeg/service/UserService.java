package com.solbeg.service;

import com.solbeg.dto.UserDTO;
import com.solbeg.mapper.UserMapper;
import com.solbeg.repository.UserR2dbcRepository;
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
    private final UserR2dbcRepository userR2dbcRepository;
    private final UserMapper userMapper;


    public Flux<UserDTO> getUsers() {
        return userR2dbcRepository.findAll()
                .map(userMapper::toDTO);
    }

    @Transactional
    public Mono<UserDTO> get(Long id) {
        return userR2dbcRepository.findById(id)
                .map(userMapper::toDTO);
    }
}
