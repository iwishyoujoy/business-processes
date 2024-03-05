package com.litres.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.litres.bookstore.dto.ReaderDTO;
import com.litres.bookstore.model.Reader;

@Mapper
public interface AutoReaderMapper {
    AutoReaderMapper MAPPER = Mappers.getMapper(AutoReaderMapper.class);

    ReaderDTO mapToReaderDTO(Reader reader);

    Reader mapToReader(ReaderDTO readerDTO);
}
