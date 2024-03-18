package com.litres.bookstore.mapper;

import org.springframework.stereotype.Component;

import com.litres.bookstore.dto.AuthorDTO;
import com.litres.bookstore.dto.ReaderDTO;
import com.litres.bookstore.model.Author;
import com.litres.bookstore.model.Reader;

@Component
public class AuthorMapper {

    public AuthorDTO mapToAuthorDTO(Author author){
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

    public Author mapToAuthor(AuthorDTO authorDTO){
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

    public void mapToUpdatedAuthor(AuthorDTO authorDTO, Author author) {
        author.setLogin(authorDTO.getLogin());
        author.setPassword(authorDTO.getPassword());
        author.setName(authorDTO.getName());
        author.setSurname(authorDTO.getSurname());
        author.setEmail(authorDTO.getEmail());
        author.setMoney(authorDTO.getMoney());
    }
}
