package com.litres.bookstore.mapper;

import com.litres.bookstore.dto.AuthorDTO;
import com.litres.bookstore.model.Author;

public class AuthorMapper {

    public static AuthorDTO mapToAuthorDTO(Author author){
        AuthorDTO authorDto = new AuthorDTO(
            author.getId(),
            author.getLogin(),
            author.getPassword(),
            author.getName(),
            author.getSurname(),
            author.getEmail(),
            author.getMoney()
        );
        return authorDto;
    }

    public static Author mapToAuthor(AuthorDTO authorDTO){
        Author author = new Author(
            authorDTO.getId(),
            authorDTO.getLogin(),
            authorDTO.getPassword(),
            authorDTO.getName(),
            authorDTO.getSurname(),
            authorDTO.getEmail(),
            authorDTO.getMoney()
        );
        return author;
    }
}
