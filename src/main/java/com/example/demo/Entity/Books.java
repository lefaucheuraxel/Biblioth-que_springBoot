package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    @Column(unique = true)
    private String isbn;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private String author;

    @Getter @Setter
    private int publicationYear;

    @Getter @Setter
    private Long numberOfPages;

}
