package com.example.MyBookShopApp.dtos;

import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.data.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDto {
    private Integer id;

    private String photo;
    private String slug;
    private String firstName;
    private String lastName;
    private String text;
    private List<BookDto> booksList;

    @Override
    public String toString() {
        return firstName + ' ' + lastName ;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

//    public List<BookDto> getBooksList() {
//        return booksList;
//    }
//
//    public void setBooksList(List<BookDto> booksList) {
//        this.booksList = booksList;
//    }


}
