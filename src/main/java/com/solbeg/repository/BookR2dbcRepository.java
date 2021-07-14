package com.solbeg.repository;

import com.solbeg.model.Book;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.r2dbc.repository.ReactorCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@R2dbcRepository(dialect = Dialect.MYSQL)
public interface BookR2dbcRepository extends ReactorCrudRepository<Book, Long> {
    @Override
    @NonNull
    @Join("author")
    Mono<Book> findById(@NonNull @NotNull Long id);

    @Override
    @NonNull
    @Join("author")
    Flux<Book> findAll();

//    @NonNull
//    @Override
//    @Transactional(Transactional.TxType.MANDATORY)
//    <S extends Book> Publisher<S> save(@NonNull @Valid @NotNull S entity);
//
//    @NonNull
//    @Override
//    @Transactional(Transactional.TxType.MANDATORY)
//    <S extends Book> Publisher<S> saveAll(@NonNull @Valid @NotNull Iterable<S> entities);

}
