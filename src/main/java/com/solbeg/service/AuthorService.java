package com.solbeg.service;

import com.solbeg.model.Author;
import com.solbeg.model.Book;
import com.solbeg.repository.AuthorR2dbcRepository;
import com.solbeg.repository.BookR2dbcRepository;
import reactor.core.publisher.Mono;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Arrays;

@Singleton
public class AuthorService {
    private final AuthorR2dbcRepository authorR2dbcRepository;
    private final BookR2dbcRepository bookR2dbcRepository;

    public AuthorService(AuthorR2dbcRepository authorR2dbcRepository, BookR2dbcRepository bookR2dbcRepository) {
        this.authorR2dbcRepository = authorR2dbcRepository;
        this.bookR2dbcRepository = bookR2dbcRepository;
    }

    @Transactional
    public Mono<Void> setupData() {
        return Mono.from(authorR2dbcRepository.save(new Author("Stephen King")))
                .flatMapMany((author -> bookR2dbcRepository.saveAll(Arrays.asList(
                        new Book("The Stand", 1000, author),
                        new Book("The Shining", 400, author)
                ))))
                .then(Mono.from(authorR2dbcRepository.save(new Author("James Patterson"))))
                .flatMapMany((author ->
                        bookR2dbcRepository.save(new Book("Along Came a Spider", 300, author))
                )).then();
    }
}