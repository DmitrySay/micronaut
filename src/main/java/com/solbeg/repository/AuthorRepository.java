package com.solbeg.repository;

import com.solbeg.model.Author;
import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.r2dbc.repository.ReactorCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@R2dbcRepository(dialect = Dialect.MYSQL)
public interface AuthorRepository extends ReactorCrudRepository<Author, Long> {

    @NonNull
    @Override
    Mono<Author> findById(@NonNull @NotNull Long aLong);

    @NonNull
    @Override
    Flux<Author> findAll();


}