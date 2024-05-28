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
public class ReaderWithWalletDTO {
    private ReaderDTO reader;
    private Wallet wallet;
}


