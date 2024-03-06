package com.litres.bookstore.mapper;

import java.util.Optional;

import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.exception.ResourceNotFoundException;
import com.litres.bookstore.model.Author;
import com.litres.bookstore.model.Book;
import com.litres.bookstore.repository.AuthorRepository;

public class BookMapper {

    private static AuthorRepository authorRepository;

    public static BookDTO mapToBookDTO(Book book){
        BookDTO bookDto = new BookDTO(
            book.getId(),
            book.getTitle(),
            book.getDescription(),
            book.getContent(),
            book.getAuthor().getId()
        );
        return bookDto;
    }

    public static Book mapToBook(BookDTO bookDTO){
        Book book = new Book(
            bookDTO.getId(),
            bookDTO.getTitle(),
            bookDTO.getDescription(),
            bookDTO.getContent(),
            getAuthorById(bookDTO.getAuthorId())
        );
        return book;
    }

    private static Author getAuthorById(Long id){
        return authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author", "id", String.valueOf(id)));
    }
}
