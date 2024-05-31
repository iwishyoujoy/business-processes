package com.litres.bookstore.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    private Long id;
    @Column(unique = true)
    private String login;
    private String password;
    private String name;
    private String surname;
    private String email;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();

    public Author(Long id, String login, String password, String name, String surname, String email){
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}
