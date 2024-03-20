package com.litres.bookstore.dto;

import lombok.Setter;

import com.litres.bookstore.model.enums.AgeRestriction;

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
    private Long authorId;
    private Float price;
// TODO https://stackoverflow.com/questions/59291371/migrating-from-springfox-swagger-2-to-springdoc-open-api
//    @Schema(value = "Age restriction of the book", allowableValues = "NONE, SIX, TWELVE, SIXTEEN, EIGHTEEN")
    private AgeRestriction ageRestriction;
}
