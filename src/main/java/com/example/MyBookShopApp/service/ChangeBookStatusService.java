package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.dtos.BookDto;

import java.io.IOException;
import java.util.List;

public interface ChangeBookStatusService {

    void addToKept(String slug, String typeId);

    void deletePostponedBook(String slug);

    List<BookDto> getPostponedBook();
}
