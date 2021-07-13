package com.solbeg.controller;

import com.solbeg.model.Author;
import com.solbeg.repository.AuthorRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.reactivex.Single;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller("/authors")
public class AuthorController {
    private final AuthorRepository authorRepository;

    @Inject
    public AuthorController(AuthorRepository repository) {
        this.authorRepository = repository;
    }

    @Get("/")
    Flux<Author> getAll() {
        return authorRepository.findAll();
    }

    @Get("/{id}")
    Mono<Author> get(@PathVariable Long id) {
        return authorRepository.findById(id);
    }

    @Post("/")
    Single<Author> create(@Valid Author author) {
        return Single.fromPublisher(authorRepository.save(author));
    }

    @Put("/{id}")
    Single<Author> update(@NotNull Long id, @Valid Author author) {
        return Single.fromPublisher(authorRepository.update(author));
    }

    @Delete("/{id}")
    Single<HttpResponse<?>> delete(@NotNull Long id) {
        return Single.fromPublisher(authorRepository.deleteById(id))
                .map(deleted -> deleted > 0 ? HttpResponse.noContent() : HttpResponse.notFound());
    }
}
