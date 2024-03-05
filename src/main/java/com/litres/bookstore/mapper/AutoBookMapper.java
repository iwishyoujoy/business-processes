package com.litres.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.litres.bookstore.dto.BookDTO;
import com.litres.bookstore.model.Book;

@Mapper
public interface AutoBookMapper {
    AutoBookMapper MAPPER = Mappers.getMapper(AutoBookMapper.class);

    BookDTO mapToBookDTO(Book book);

    Book mapToBook(BookDTO bookDTO);
}

