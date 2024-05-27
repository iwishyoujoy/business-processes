package com.litres.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MultipleWalletRequest {
    private Long authorId;
    private Long readerId;
    private Float money;
}


