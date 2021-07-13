package com.solbeg.controller;

import com.solbeg.model.Author;
import com.solbeg.repository.AuthorRepository;
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
public class AuthorController {
    private final AuthorRepository authorRepository;

    @Get(value = "/", produces = MediaType.APPLICATION_JSON_STREAM)
    Flux<Author> getAll() {
        return authorRepository.findAll();
    }

    @Get("/{id}")
    Mono<Author> get(@PathVariable @NotNull Long id) {
        return authorRepository.findById(id);
    }

    @Post("/")
    Mono<Author> create(@Body @Valid Author author) {
        return authorRepository.save(author).thenReturn(author);
    }

    @Put("/{id}")
    Mono<Author> update(@NotNull Long id, @Valid Author author) {
        author.setId(id);
        return authorRepository.update(author).thenReturn(author);
    }

    @Delete("/{id}")
    Mono<HttpResponse<?>> delete(@NotNull Long id) {
        return authorRepository
                .deleteById(id)
                .map(deleted -> deleted > 0 ? HttpResponse.noContent() : HttpResponse.notFound());
    }
}
