package com.litres.bookstore.mapper;

import org.springframework.stereotype.Component;

import com.litres.bookstore.dto.ReaderDTO;
import com.litres.bookstore.model.Reader;
import com.litres.bookstore.service.WalletService;

@Component
public class ReaderMapper {

    private final WalletService walletService;

    public ReaderMapper(WalletService walletService) {
        this.walletService = walletService;
    }

    public ReaderDTO mapToReaderDTO(Reader reader){
        Float money = walletService.getWalletByUserId(reader.getId()).getMoney();

        ReaderDTO readerDto = new ReaderDTO(
            reader.getId(),
            reader.getLogin(),
            reader.getPassword(),
            reader.getName(),
            reader.getSurname(),
            reader.getEmail(),
            money,
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
        reader.setBirthDate(readerDTO.getBirthDate());
    }
}
