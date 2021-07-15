package com.solbeg.fakedata;


import com.github.javafaker.Faker;
import com.solbeg.model.Author;
import com.solbeg.model.Book;
import com.solbeg.model.User;
import com.solbeg.repository.AuthorRepository;
import com.solbeg.repository.BookRepository;
import com.solbeg.repository.UserRepository;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Used to insert data into db on startup
 */
@Slf4j
@RequiredArgsConstructor
@Singleton
@Requires(notEnv = Environment.TEST)
public class FakeDataProvider {
    public static final int LOOP_NUMBER = 50;
    public static final int AUTHOR_LOOP_NUMBER = 20;
    public static final int PASSWORD_LENGTH = 8;
    public static final String EMAIL_PROVIDER = "@gmail.com";
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final Faker faker;

    @EventListener
    public void initUsers(StartupEvent event) {
        if (userRepository.findAll().isEmpty()) {
            log.info(String.format("Start to generate [%d] fake users...", LOOP_NUMBER));
            List<User> fakeUsers = new ArrayList<>();
            IntStream.range(0, LOOP_NUMBER)
                    .forEach(number -> fakeUsers.add(generateFakeUser()));
            userRepository.saveAll(fakeUsers);
        }
    }

    @EventListener
    public void initAuthorsAndBooks(StartupEvent event) {
        if (authorRepository.findAll().isEmpty() && bookRepository.findAll().isEmpty()) {
            log.info(String.format("Start to generate [%d] fake authors with book...", AUTHOR_LOOP_NUMBER));
            List<Author> fakeAuthors = new ArrayList<>();
            List<Book> fakeBooks = new ArrayList<>();
            IntStream.range(0, AUTHOR_LOOP_NUMBER)
                    .forEach(number -> {
                        Book book = generateFakeAuthor();
                        fakeAuthors.add(book.getAuthor());
                        fakeBooks.add(book);
                    });
            authorRepository.saveAll(fakeAuthors);
            bookRepository.saveAll(fakeBooks);
        }
    }

    public User generateFakeUser() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = firstName + "_" + lastName + EMAIL_PROVIDER;
        String password = faker.number().digits(PASSWORD_LENGTH);
        return new User(email.toLowerCase(), password);
    }

    public com.solbeg.model.Book generateFakeAuthor() {
        com.github.javafaker.Book fakerBook = faker.book();
        int pages = faker.number().numberBetween(20, 1000);
        String title = fakerBook.title();
        String fakerAuthor = fakerBook.author();
        Author author = new Author(fakerAuthor);
        return new com.solbeg.model.Book(title, pages, author);
    }
}
