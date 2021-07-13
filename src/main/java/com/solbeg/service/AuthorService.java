package com.solbeg.service;

import com.solbeg.model.Author;
import com.solbeg.model.Book;
import com.solbeg.repository.AuthorRepository;
import com.solbeg.repository.BookRepository;
import reactor.core.publisher.Mono;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Arrays;

@Singleton
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Mono<Void> setupData() {
        return Mono.from(authorRepository.save(new Author("Stephen King")))
                .flatMapMany((author -> bookRepository.saveAll(Arrays.asList(
                        new Book("The Stand", 1000, author),
                        new Book("The Shining", 400, author)
                ))))
                .then(Mono.from(authorRepository.save(new Author("James Patterson"))))
                .flatMapMany((author ->
                        bookRepository.save(new Book("Along Came a Spider", 300, author))
                )).then();
    }
}