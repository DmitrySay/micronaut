package com.solbeg.repository;

import com.solbeg.model.Author;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;

@R2dbcRepository(dialect = Dialect.MYSQL)
public interface AuthorRepository extends JpaRepository<Author, Long> {
}