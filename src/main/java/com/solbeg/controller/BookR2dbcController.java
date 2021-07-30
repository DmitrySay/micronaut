package com.solbeg.controller;

import com.solbeg.model.Book;
import com.solbeg.repository.BookR2dbcRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Secured(SecurityRule.IS_AUTHENTICATED)
@RequiredArgsConstructor
@Controller("/books")
@Tag(name = "books")
public class BookR2dbcController {
    private final BookR2dbcRepository bookR2dbcRepository;

    @Operation(summary = "Get book", description = "Gets a book by its ID")
    @ApiResponse(responseCode = "200", description = "OK",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))})
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @ApiResponse(responseCode = "500", description = "Server error")
    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    Mono<Book> get(@NotNull Long id) {
        return bookR2dbcRepository.findById(id);
    }

    @Operation(summary = "Get books", description = "Get a stream of Books")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_STREAM, schema = @Schema(implementation = Book.class)))
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @ApiResponse(responseCode = "500", description = "Server error")
    @Get(produces = MediaType.APPLICATION_JSON_STREAM)
    Flux<Book> getAll() {
        return bookR2dbcRepository.findAll();
    }

    @Operation(summary = "Create book", description = "Create book",
            requestBody = @RequestBody(description = "Book entity",
                    required = true, content = @Content(schema = @Schema(implementation = Book.class)))
    )
    @ApiResponse(responseCode = "201", description = "Created")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @Post
    Mono<HttpResponse<Book>> create(@Body @Valid Book book) {
        return bookR2dbcRepository.save(book)
                .thenReturn(HttpResponse.created(book));
    }

    @Operation(summary = "Update book", description = "Update book by its id",
            requestBody = @RequestBody(description = "Book entity",
                    required = true, content = @Content(schema = @Schema(implementation = Book.class)))
    )
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @Put("/{id}")
    Mono<HttpResponse<Book>> update(@NotNull Long id, @Valid Book book) {
        book.setId(id);
        return bookR2dbcRepository.update(book)
                .thenReturn(HttpResponse.ok(book));
    }

    @Operation(summary = "Delete book", description = "Delete book by its id")
    @ApiResponse(responseCode = "200", description = "No Content")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @Delete("/{id}")
    Mono<HttpResponse<?>> delete(@NotNull Long id) {
        return bookR2dbcRepository
                .deleteById(id)
                .map(deleted -> deleted > 0 ? HttpResponse.noContent() : HttpResponse.notFound());
    }

}
