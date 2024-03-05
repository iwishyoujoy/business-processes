package com.litres.bookstore.mapper;

import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.model.Book;

public class BookMapper {

    public static BookDTO mapToBookDTO(Book book){
        BookDTO bookDto = new BookDTO(
            book.getId(),
            book.getTitle(),
            book.getDescription(),
            book.getContent(),
            book.getAuthor()
        );
        return bookDto;
    }

    public static Book mapToBook(BookDTO bookDTO){
        Book book = new Book(
            bookDTO.getId(),
            bookDTO.getTitle(),
            bookDTO.getDescription(),
            bookDTO.getContent(),
            bookDTO.getAuthor()
        );
        return book;
    }
}
