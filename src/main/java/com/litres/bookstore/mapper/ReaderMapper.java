package com.litres.bookstore.mapper;

import com.litres.bookstore.dto.ReaderDTO;
import com.litres.bookstore.model.Reader;

public class ReaderMapper {

    public static ReaderDTO mapToReaderDTO(Reader reader){
        ReaderDTO readerDto = new ReaderDTO(
            reader.getId(),
            reader.getLogin(),
            reader.getPassword(),
            reader.getName(),
            reader.getSurname(),
            reader.getEmail(),
            reader.getMoney()
        );
        return readerDto;
    }

    public static Reader mapToReader(ReaderDTO readerDTO){
        Reader reader = new Reader(
            readerDTO.getId(),
            readerDTO.getLogin(),
            readerDTO.getPassword(),
            readerDTO.getName(),
            readerDTO.getSurname(),
            readerDTO.getEmail(),
            readerDTO.getMoney()
        );
        return reader;
    }
}
