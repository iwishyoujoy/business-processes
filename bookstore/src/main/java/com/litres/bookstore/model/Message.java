package com.litres.bookstore.model;

import java.io.Serializable;

import com.litres.bookstore.enums.MessageTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable{
    private MessageTypes type;
    private Long authorId;
    private Long readerId;
    private Float money;
}
