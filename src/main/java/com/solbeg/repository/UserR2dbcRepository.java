package com.solbeg.repository;

import com.solbeg.model.User;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.r2dbc.repository.ReactorCrudRepository;

import java.util.Optional;

@R2dbcRepository(dialect = Dialect.MYSQL)
public interface UserR2dbcRepository extends ReactorCrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}