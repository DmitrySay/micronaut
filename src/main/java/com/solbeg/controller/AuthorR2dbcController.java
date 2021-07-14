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
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@RequiredArgsConstructor
@Controller("/authors")
public class AuthorR2dbcController {
    private final AuthorService authorService;

    @Get(value = "/", produces = MediaType.APPLICATION_JSON_STREAM)
    Flux<Author> getAll() {
        return authorService.findAll()
                .delayElements(Duration.ofSeconds(2));
    }

    @Get("/{id}")
    Mono<MutableHttpResponse<Author>> get(@PathVariable @NotNull Long id) {
        return authorService.findById(id)
                .map(HttpResponse::ok)
                .defaultIfEmpty(HttpResponse.notFound());
    }


    @Post("/")
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
