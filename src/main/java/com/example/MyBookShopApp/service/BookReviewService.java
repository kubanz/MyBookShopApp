package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.dtos.BookReviewDto;

import java.util.List;

public interface BookReviewService {

    List<BookReviewDto> getBookReview(String slug);
    void saveBookReview(String text, String slug);
}
