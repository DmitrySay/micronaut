package com.solbeg.controller;

import com.solbeg.dto.UserDTO;
import com.solbeg.service.UserService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Secured(SecurityRule.IS_AUTHENTICATED)
@RequiredArgsConstructor
@Controller("/users")
@Tag(name = "users")
public class UserR2dbcController {
    private final UserService userService;

    @Get
    public Flux<UserDTO> getAll() {
        return userService.getUsers();
    }

    @Get("/{id}")
    public Mono<UserDTO> get(@PathVariable Long id) {
        return userService.get(id);
    }

}
