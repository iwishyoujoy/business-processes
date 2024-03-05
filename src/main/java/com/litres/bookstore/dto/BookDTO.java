package com.litres.bookstore.dto;

import com.litres.bookstore.model.Author;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String description;
    private String content;
    private Author author;
}
