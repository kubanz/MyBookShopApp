package com.example.MyBookShopApp.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookDto {

    private Integer id;

    private Date pubdate;

    @JsonIgnore
    private AuthorDto author;

    public String authorsFullName;

    private Integer isBestseller;

    private String slug;

    private String title;

    private String image;

    private List<BookFileDto> bookFileList = new ArrayList<>();

    private String description;

    private Integer priceOld;

    private Double price;

    private Integer popularity;

    private Integer tagID;

    @JsonProperty
    public Integer discountPrice(){
        Integer discountedPrice = priceOld - Math.toIntExact(Math.round(price * priceOld));
        return discountedPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getPubdate() {
        return pubdate;
    }

    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }

    public AuthorDto getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDto author) {
        this.author = author;
    }

    public String getAuthorsFullName() {
        return authorsFullName;
    }

    public void setAuthorsFullName(String authorsFullName) {
        this.authorsFullName = authorsFullName;
    }

    public Integer getIsBestseller() {
        return isBestseller;
    }

    public void setIsBestseller(Integer isBestseller) {
        this.isBestseller = isBestseller;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<BookFileDto> getBookFileList() {
        return bookFileList;
    }

    public void setBookFileList(List<BookFileDto> bookFileList) {
        this.bookFileList = bookFileList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriceOld() {
        return priceOld;
    }

    public void setPriceOld(Integer priceOld) {
        this.priceOld = priceOld;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public Integer getTagID() {
        return tagID;
    }

    public void setTagID(Integer tagID) {
        this.tagID = tagID;
    }
}
