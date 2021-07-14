package com.solbeg.repository;

import com.solbeg.model.User;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;

import java.util.Optional;

@R2dbcRepository(dialect = Dialect.MYSQL)
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

}