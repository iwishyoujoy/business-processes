package com.litres.bookstore.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.litres.bookstore.model.enums.AgeRestriction;
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
    private Float price;

    @Column(name = "age_restriction")
    private AgeRestriction ageRestriction;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.ALL)
    private List<Reader> readers = new ArrayList<>();

    public Book (Long id, String title, String description, String content, Author author, Float price, AgeRestriction ageRestriction){
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.author = author;
        this.price = price;
        this.ageRestriction = ageRestriction;
    }
}
