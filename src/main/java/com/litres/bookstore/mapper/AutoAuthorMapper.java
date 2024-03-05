package com.litres.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.litres.bookstore.dto.AuthorDTO;
import com.litres.bookstore.model.Author;

@Mapper
public interface AutoAuthorMapper {
    AutoAuthorMapper MAPPER = Mappers.getMapper(AutoAuthorMapper.class);

    AuthorDTO mapToAuthorDTO(Author author);

    Author mapToAuthor(AuthorDTO authorDTO);
}
