package com.litres.bookstore.model.enums;

import lombok.Getter;

@Getter
public enum AgeRestriction {
    NONE(0),
    SIX(6),
    TWELVE(12),
    SIXTEEN(16),
    EIGHTEEN(18);

    private final int age;

    AgeRestriction(int age) {
        this.age = age;
    }

}
