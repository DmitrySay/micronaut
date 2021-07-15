package com.solbeg.fakedata;


import com.github.javafaker.Faker;
import com.solbeg.model.User;
import com.solbeg.repository.UserRepository;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Used to insert data into db on startup
 */
@Slf4j
@RequiredArgsConstructor
@Singleton
@Requires(notEnv = Environment.TEST)
public class FakeDataProvider {
    public static final int LOOP_NUMBER = 50;
    public static final int PASSWORD_LENGTH = 8;
    public static final String EMAIL_PROVIDER = "@gmail.com";
    private final UserRepository userRepository;
    private final Faker faker;

    @EventListener
    public void init(StartupEvent event) {
        if (userRepository.findAll().isEmpty()) {
            log.info(String.format("Start to generate [%d] fake users...", LOOP_NUMBER));
            List<User> fakeUsers = new ArrayList<>();
            IntStream.range(0, LOOP_NUMBER)
                    .forEach(number -> fakeUsers.add(generateFakeUser()));
            userRepository.saveAll(fakeUsers);
        }
    }

    public User generateFakeUser() {
        String firstName = faker.name().firstName(); // Emory
        String lastName = faker.name().lastName(); // Barton
        String email = firstName + "_" + lastName + EMAIL_PROVIDER;
        String password = faker.number().digits(PASSWORD_LENGTH);
        return new User(email.toLowerCase(), password);
    }
}
