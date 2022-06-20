package com.example.MyBookShopApp.data;

public class Book {

    private Integer id;
    private String price;
    private String priceOld;
    private String title;
    private int authorId;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", price='" + price + '\'' +
                ", priceOld='" + priceOld + '\'' +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceOld() {
        return priceOld;
    }

    public void setPriceOld(String priceOld) {
        this.priceOld = priceOld;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
