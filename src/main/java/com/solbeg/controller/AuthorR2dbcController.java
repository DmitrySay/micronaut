package com.solbeg.controller;

import com.solbeg.model.Author;
import com.solbeg.repository.AuthorR2dbcRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Controller("/authors")
public class AuthorR2dbcController {
    private final AuthorR2dbcRepository authorR2dbcRepository;

    @Get(value = "/", produces = MediaType.APPLICATION_JSON_STREAM)
    Flux<Author> getAll() {
        return authorR2dbcRepository.findAll();
    }

    @Get("/{id}")
    Mono<Author> get(@PathVariable @NotNull Long id) {
        return authorR2dbcRepository.findById(id);
    }

    @Post("/")
    Mono<Author> create(@Body @Valid Author author) {
        return authorR2dbcRepository.save(author).thenReturn(author);
    }

    @Put("/{id}")
    Mono<Author> update(@NotNull Long id, @Valid Author author) {
        author.setId(id);
        return authorR2dbcRepository.update(author).thenReturn(author);
    }

    @Delete("/{id}")
    Mono<HttpResponse<?>> delete(@NotNull Long id) {
        return authorR2dbcRepository
                .deleteById(id)
                .map(deleted -> deleted > 0 ? HttpResponse.noContent() : HttpResponse.notFound());
    }
}
