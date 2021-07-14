package com.solbeg.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Data
@Entity(name = "author")
@Table(name = "author", schema = "mn")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public Author(String name) {
        this.name = name;
    }

}
