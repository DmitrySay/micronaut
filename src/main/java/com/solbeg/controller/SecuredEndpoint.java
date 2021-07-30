package com.solbeg.controller;

import com.solbeg.model.User;
import com.solbeg.repository.UserRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/secured")
@Tag(name = "auth")
public class SecuredEndpoint {
    private final UserRepository userRepository;

    @Operation(summary = "Get authorized user", description = "Get authorized user")
    @ApiResponse(responseCode = "200", description = "OK",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))})
    @Get("/currentUser")
    public Optional<User> status(Principal principal) {
        Authentication details = (Authentication) principal;
        log.debug("User Details: {}", details.getAttributes());
        String email = principal.getName();
        log.debug("User email: {}", email);
        return userRepository.findByEmail(email);
    }
}
