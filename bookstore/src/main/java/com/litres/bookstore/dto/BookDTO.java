package com.litres.bookstore.dto;

import lombok.Setter;

import com.litres.bookstore.model.enums.AgeRestriction;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Age restriction of the book", allowableValues = {"NONE", "SIX", "TWELVE", "SIXTEEN", "EIGHTEEN"})
    private AgeRestriction ageRestriction;
}
