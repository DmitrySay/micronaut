package com.solbeg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "book")
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private int pages;

    @EqualsAndHashCode.Exclude
    @ManyToOne(targetEntity = Author.class,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    public Book(String title, int pages, Author author) {
        this.title = title;
        this.pages = pages;
        this.author = author;
    }
}
