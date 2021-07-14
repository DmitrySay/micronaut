package com.solbeg.service;

import com.solbeg.dto.UserDTO;
import com.solbeg.exception.UserNotFoundException;
import com.solbeg.model.User;
import com.solbeg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class UserService {
    public static final String USER_NOT_FOUND_MSG = "User with id=[%s] not found";
    private final UserRepository userRepository;

    @Transactional
    public List<UserDTO> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getEmail()))
                .collect(Collectors.toList());

    }

    @Transactional
    public UserDTO get(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MSG, id)));
        return new UserDTO(user.getId(), user.getEmail());
    }
}
