package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class BooksPageController {


    private final BookService bookService;

    @Autowired
    public BooksPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("bookList")
    public List<Book> bookList(){
        return bookService.getBooksData();
    }

    @GetMapping("/books/popular")
    public String popularBookPage(){
        return "books/popular";
    }

    @GetMapping("/books/recent")
    public String recentBookPage(){
        return "books/recent";
    }

}
