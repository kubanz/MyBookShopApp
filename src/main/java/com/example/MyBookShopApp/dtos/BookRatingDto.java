package com.example.MyBookShopApp.dtos;

import com.example.MyBookShopApp.data.user.UserEntity;

public class BookRatingDto {

    private Long id;
    private int rating;
    private BookDto book;
    private UserEntityDto userID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public UserEntityDto getUserID() {
        return userID;
    }

    public void setUserID(UserEntityDto userID) {
        this.userID = userID;
    }
}
