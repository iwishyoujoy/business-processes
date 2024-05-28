package com.litres.bookstore.dto;

import com.litres.bookstore.model.Wallet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorWithWalletDTO {
    private AuthorDTO author;
    private Wallet wallet;
}

