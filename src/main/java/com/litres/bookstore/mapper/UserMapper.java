package com.litres.bookstore.mapper;

import com.litres.bookstore.dto.AuthorDTO;
import com.litres.bookstore.dto.ReaderDTO;
import com.litres.bookstore.dto.UserDTO;
import com.litres.bookstore.model.Role;
import com.litres.bookstore.model.User;
import com.litres.bookstore.model.enums.UserRole;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserMapper {

    public User mapToUser(AuthorDTO authorDTO) {
        User user = new User(
                authorDTO.getId(),
                authorDTO.getLogin(),
                authorDTO.getPassword(),
                Collections.singleton(new Role(UserRole.ROLE_AUTHOR.getId(), UserRole.ROLE_AUTHOR.name()))
        );
        return user;
    }

    public User mapToUser(ReaderDTO readerDTO) {
        User user = new User(
                readerDTO.getId(),
                readerDTO.getLogin(),
                readerDTO.getPassword(),
                Collections.singleton(new Role(UserRole.ROLE_READER.getId(), UserRole.ROLE_READER.name()))
        );
        return user;
    }

    public User mapToUser(UserDTO userDTO) {
        User user = new User(
                0L,
                userDTO.getLogin(),
                userDTO.getPassword(),
                null
        );
        return user;
    }
}
