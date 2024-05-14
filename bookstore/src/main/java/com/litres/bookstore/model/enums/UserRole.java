package com.litres.bookstore.model.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ROLE_AUTHOR(0),
    ROLE_READER(1);

    private final long id;

    UserRole(int age) {
        this.id = age;
    }
}
