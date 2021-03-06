package com.solbeg.controller;

import com.solbeg.model.Author;
import com.solbeg.service.AuthorService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Secured(SecurityRule.IS_AUTHENTICATED)
@RequiredArgsConstructor
@Controller("/authors")
@Tag(name = "authors")
public class AuthorR2dbcController {
    private final AuthorService authorService;

    @Get(produces = MediaType.APPLICATION_JSON_STREAM)
    Flux<Author> getAll() {
        return authorService.findAll();
    }

    @Get("/{id}")
    Mono<MutableHttpResponse<Author>> get(@PathVariable @NotNull Long id) {
        return authorService.findById(id)
                .map(HttpResponse::ok)
                .defaultIfEmpty(HttpResponse.notFound())
                .onErrorReturn(HttpClientResponseException.class, HttpResponse.notFound());
    }

    @Post
    Mono<Author> create(@Body @Valid Author author) {
        return authorService.save(author)
                .thenReturn(author);
    }

    @Put("/{id}")
    Mono<Author> update(@NotNull Long id, @Valid Author author) {
        author.setId(id);
        return authorService.update(author)
                .thenReturn(author);
    }

    @Delete("/{id}")
    Mono<HttpResponse<?>> delete(@NotNull Long id) {
        return authorService
                .deleteById(id)
                .map(deleted -> deleted > 0 ? HttpResponse.noContent() : HttpResponse.notFound());
    }
}
