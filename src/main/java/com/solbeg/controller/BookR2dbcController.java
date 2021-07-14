package com.solbeg.controller;

import com.solbeg.model.Book;
import com.solbeg.repository.BookR2dbcRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Controller("/books")
public class BookR2dbcController {
    private final BookR2dbcRepository bookR2dbcRepository;

    @Get(value = "/", produces = MediaType.APPLICATION_JSON_STREAM)
    Flux<Book> getAll() {
        return bookR2dbcRepository.findAll();
    }

    @Get("/{id}")
    Mono<Book> get(@NotNull Long id) {
        return bookR2dbcRepository.findById(id);
    }

    @Post("/")
    Mono<HttpResponse<Book>> create(@Body @Valid Book book) {
        return bookR2dbcRepository.save(book)
                .thenReturn(HttpResponse.created(book));
    }

    @Put("/{id}")
    Mono<HttpResponse<Book>> update(@NotNull Long id, @Valid Book book) {
        book.setId(id);
        return bookR2dbcRepository.update(book)
                .thenReturn(HttpResponse.ok(book));
    }

    @Delete("/{id}")
    Mono<HttpResponse<?>> delete(@NotNull Long id) {
        return bookR2dbcRepository
                .deleteById(id)
                .map(deleted -> deleted > 0 ? HttpResponse.noContent() : HttpResponse.notFound());
    }

}
