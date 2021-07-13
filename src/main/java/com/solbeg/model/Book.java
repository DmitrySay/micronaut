package com.solbeg.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity(name = "book")
@Table(name = "book", schema = "mn")
public class Book {
    @GeneratedValue
    @Id
    private Long id;
    private String title;
    private int pages;

    @ManyToOne(targetEntity = Author.class)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    public Book(String title, int pages, Author author) {
        this.title = title;
        this.pages = pages;
        this.author = author;
    }
}
