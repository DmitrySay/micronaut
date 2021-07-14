package com.solbeg.service;

import com.solbeg.model.Author;
import com.solbeg.repository.AuthorR2dbcRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Singleton;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Singleton
public class AuthorService {
    private final AuthorR2dbcRepository authorR2dbcRepository;

    public Flux<Author> findAll() {
        return authorR2dbcRepository.findAll();
    }

    public Mono<Author> findById(Long id) {
        return authorR2dbcRepository.findById(id);
    }

    @Transactional
    public Mono<Author> save(Author author) {
        return authorR2dbcRepository.save(author);
    }

    @Transactional
    public Mono<Author> update(Author author) {
        return authorR2dbcRepository.update(author);
    }

    @Transactional
    public Mono<Long> deleteById(Long id) {
        return authorR2dbcRepository.deleteById(id);
    }


//    Example
//    @Transactional
//    public Mono<Void> setupData() {
//        return Mono.from(authorR2dbcRepository.save(new Author("Stephen King")))
//                .flatMapMany((author -> bookR2dbcRepository.saveAll(Arrays.asList(
//                        new Book("The Stand", 1000, author),
//                        new Book("The Shining", 400, author)
//                ))))
//                .then(Mono.from(authorR2dbcRepository.save(new Author("James Patterson"))))
//                .flatMapMany((author ->
//                        bookR2dbcRepository.save(new Book("Along Came a Spider", 300, author))
//                )).then();
//    }
}