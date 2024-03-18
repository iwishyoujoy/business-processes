package com.litres.bookstore.mapper;

import org.springframework.stereotype.Component;

import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.exception.ResourceNotFoundException;
import com.litres.bookstore.model.Author;
import com.litres.bookstore.model.Book;
import com.litres.bookstore.repository.AuthorRepository;

@Component
public class BookMapper {

    private AuthorRepository authorRepository;

    public BookMapper(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public BookDTO mapToBookDTO(Book book){
        BookDTO bookDto = new BookDTO(
            book.getId(),
            book.getTitle(),
            book.getDescription(),
            book.getContent(),
            book.getAuthor() != null ? book.getAuthor().getId() : null,
            book.getPrice()
        );
        return bookDto;
    }

    public Book mapToBook(BookDTO bookDTO){
        Author author = getAuthorById(bookDTO.getAuthorId());
        Book book = new Book(
            bookDTO.getId(),
            bookDTO.getTitle(),
            bookDTO.getDescription(),
            bookDTO.getContent(),
            author,
            bookDTO.getPrice()
        );
        return book;
    }

    private Author getAuthorById(Long id){
        return authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author", "id", String.valueOf(id)));
    }

    public void mapToUpdatedBook(BookDTO bookDTO, Book book) {
        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setContent(bookDTO.getContent());
        book.setPrice(bookDTO.getPrice());
        if (bookDTO.getAuthorId() != null) {
            Author author = getAuthorById(bookDTO.getAuthorId());
            book.setAuthor(author);
        }
    }
}
