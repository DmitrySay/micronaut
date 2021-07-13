package com.solbeg.controller;

import com.solbeg.model.Book;
import com.solbeg.repository.BookRepository;
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
public class BookController {
    private final BookRepository bookRepository;

    @Get(value = "/", produces = MediaType.APPLICATION_JSON_STREAM)
    Flux<Book> getAll() {
        return bookRepository.findAll();
    }

    @Get("/{id}")
    Mono<Book> get(@NotNull Long id) {
        return bookRepository.findById(id);
    }

    @Post("/")
    Mono<HttpResponse<Book>> create(@Body @Valid Book book) {
        return bookRepository.save(book)
                .thenReturn(HttpResponse.created(book));
    }

    @Put("/{id}")
    Mono<HttpResponse<Book>> update(@NotNull Long id, @Valid Book book) {
        book.setId(id);
        return bookRepository.update(book)
                .thenReturn(HttpResponse.ok(book));
    }

    @Delete("/{id}")
    Mono<HttpResponse<?>> delete(@NotNull Long id) {
        return bookRepository
                .deleteById(id)
                .map(deleted -> deleted > 0 ? HttpResponse.noContent() : HttpResponse.notFound());
    }

}
