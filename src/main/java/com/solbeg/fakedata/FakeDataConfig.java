package com.solbeg.fakedata;

import com.github.javafaker.Faker;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

import java.util.Locale;

@Factory
public class FakeDataConfig {
    @Bean
    public Faker faker() {
        return new Faker(new Locale("en-US"));
    }
}
