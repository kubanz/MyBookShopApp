package com.example.MyBookShopApp.dtos;

import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.data.Book;
import lombok.Data;

import java.util.List;

@Data
public class AuthorDto {

    private Author author;

    public AuthorDto(Author authors) {
        this.author = authors;
    }
}
