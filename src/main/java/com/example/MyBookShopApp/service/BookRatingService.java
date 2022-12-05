package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.dtos.RatingPageDto;

public interface BookRatingService {
    RatingPageDto getBookRating(String slug, int userId);

    void saveUpdateBookRate(String slug, int userId, int value);
}
