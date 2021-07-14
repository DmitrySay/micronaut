package com.solbeg.repository;

import com.solbeg.model.Author;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.r2dbc.repository.ReactorCrudRepository;

@R2dbcRepository(dialect = Dialect.MYSQL)
public interface AuthorR2dbcRepository extends ReactorCrudRepository<Author, Long> {
}