package com.solbeg.controller;

import com.solbeg.dto.UserDTO;
import com.solbeg.service.UserService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Controller("/users")
public class UserController {
    private final UserService userService;

    @Get
    public List<UserDTO> getAll() {
        return userService.getUsers();
    }

    @Get("/{id}")
    public UserDTO get(@PathVariable Long id) {
        return userService.get(id);
    }

}
