package com.litres.bookstore.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany(mappedBy = "books")
    private List<Reader> readers = new ArrayList<>();

    public Book (Long id, String title, String description, String content, Author author){
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.author = author;
    }
}
