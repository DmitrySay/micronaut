package com.solbeg.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@NoArgsConstructor
@Data
@Entity(name = "author")
@Table(name = "author", schema = "mn")
public class Author {
    @GeneratedValue
    @Id
    private Long id;
    private String name;

    public Author(String name) {
        this.name = name;
    }

}
