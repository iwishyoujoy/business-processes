package com.litres.bookstore.mapper;

import org.springframework.stereotype.Component;

import com.litres.bookstore.dto.ReaderDTO;
import com.litres.bookstore.model.Reader;

@Component
public class ReaderMapper {

    public ReaderDTO mapToReaderDTO(Reader reader){
        ReaderDTO readerDto = new ReaderDTO(
            reader.getId(),
            reader.getLogin(),
            reader.getPassword(),
            reader.getName(),
            reader.getSurname(),
            reader.getEmail(),
            reader.getMoney(),
            reader.getBirthDate()
        );
        return readerDto;
    }

    public Reader mapToReader(ReaderDTO readerDTO){
        Reader reader = new Reader(
            readerDTO.getId(),
            readerDTO.getLogin(),
            readerDTO.getPassword(),
            readerDTO.getName(),
            readerDTO.getSurname(),
            readerDTO.getEmail(),
            readerDTO.getMoney(),
            readerDTO.getBirthDate()
        );
        return reader;
    }

    public void mapToUpdatedReader(ReaderDTO readerDTO, Reader reader) {
        reader.setLogin(readerDTO.getLogin());
        reader.setPassword(readerDTO.getPassword());
        reader.setName(readerDTO.getName());
        reader.setSurname(readerDTO.getSurname());
        reader.setEmail(readerDTO.getEmail());
        reader.setMoney(readerDTO.getMoney());
        reader.setBirthDate(readerDTO.getBirthDate());
    }
}
