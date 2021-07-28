package com.solbeg.controller;

import com.solbeg.model.User;
import com.solbeg.repository.UserRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/secured")
public class SecuredEndpoint {
    private final UserRepository userRepository;


    @Get("/currentUser")
    public Optional<User> status(Principal principal) {
        Authentication details = (Authentication) principal;
        log.debug("User Details: {}", details.getAttributes());
        String email = principal.getName();
        log.debug("User email: {}", email);
        return userRepository.findByEmail(email);
    }
}
