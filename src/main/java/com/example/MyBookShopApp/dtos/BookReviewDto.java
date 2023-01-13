package com.example.MyBookShopApp.dtos;

import com.example.MyBookShopApp.data.Book;

import javax.persistence.*;
import java.time.LocalDateTime;

public class BookReviewDto {

    /*@ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")*/
    private BookDto book;

    private UserEntityDto userId;

    private LocalDateTime time;

    private String text;

    private int rating;

//    public BookReviewDto() {
//    }

    public BookReviewDto(BookDto book, UserEntityDto userId, LocalDateTime time, String text, int rating) {
        this.book = book;
        this.userId = userId;
        this.time = time;
        this.text = text;
        this.rating = rating;
    }

    public BookReviewDto() {

    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public UserEntityDto getUserId() {
        return userId;
    }

    public void setUserId(UserEntityDto userId) {
        this.userId = userId;
    }

    /*public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }*/

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
