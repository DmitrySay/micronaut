package com.solbeg.controller;

import com.solbeg.model.Book;
import com.solbeg.repository.BookRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.reactivex.Single;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller("/books")
public class BookController {
    private final BookRepository bookRepository;

    @Inject
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Post("/")
    Single<Book> create(@Valid Book book) {
        return Single.fromPublisher(bookRepository.save(book));
    }

    @Get("/")
    Flux<Book> all() {
        return bookRepository.findAll();
    }

    @Get("/{id}")
    Mono<Book> show(Long id) {
        return bookRepository.findById(id);
    }

    @Put("/{id}")
    Single<Book> update(@NotNull Long id, @Valid Book book) {
        return Single.fromPublisher(bookRepository.update(book));
    }

    @Delete("/{id}")
    Single<HttpResponse<?>> delete(@NotNull Long id) {
        return Single.fromPublisher(bookRepository.deleteById(id))
                .map(deleted -> deleted > 0 ? HttpResponse.noContent() : HttpResponse.notFound());
    }

}
