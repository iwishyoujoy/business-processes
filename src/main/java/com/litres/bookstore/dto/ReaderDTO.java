package com.litres.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReaderDTO {
    private Long id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String email;
    private Float money;
}