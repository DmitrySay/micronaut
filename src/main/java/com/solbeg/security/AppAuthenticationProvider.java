package com.solbeg.security;

import com.solbeg.model.User;
import com.solbeg.repository.UserRepository;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Singleton
public class AppAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest,
                                                          AuthenticationRequest<?, ?> authenticationRequest) {
        return Flowable.create(emitter -> {
            final String identity = (String) authenticationRequest.getIdentity(); //check cast exceptions
            final String secret = (String) authenticationRequest.getSecret();
            log.debug("User {} tries to login...", identity);
            final Optional<User> maybeUser = userRepository.findByEmail(identity);
            if (maybeUser.isPresent() && maybeUser.get().getPassword().equals(secret)) {
                log.debug("User logged in.");
                final JwtUserDetails userDetails =
                        new JwtUserDetails(maybeUser.get(), Collections.singletonList("ROLE_USER"));
                emitter.onNext(userDetails);
                emitter.onComplete();
                return;
            } else {
                log.debug("No user found with email: {}", identity);
            }
            emitter.onError(new AuthenticationException(new AuthenticationFailed("Wrong username or password")));
        }, BackpressureStrategy.ERROR);
    }
}
