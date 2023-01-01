package com.example.MyBookShopApp.dtos;

public class RatingPageDto {

    private long ratingCount;
    private long rating;
    private long starOneCount;
    private long starTwoCount;
    private long starThreeCount;
    private long starFourCount;
    private long starFiveCount;
    private long rateBook;


    public RatingPageDto(long ratingCount, long rating, long starOneCount, long starTwoCount, long starThreeCount, long starFourCount, long starFiveCount, long rateBook) {
        this.ratingCount = ratingCount;
        this.rating = rating;
        this.starOneCount = starOneCount;
        this.starTwoCount = starTwoCount;
        this.starThreeCount = starThreeCount;
        this.starFourCount = starFourCount;
        this.starFiveCount = starFiveCount;
        this.rateBook = rateBook;
    }

    public long getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(long ratingCount) {
        this.ratingCount = ratingCount;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public long getStarOneCount() {
        return starOneCount;
    }

    public void setStarOneCount(long starOneCount) {
        this.starOneCount = starOneCount;
    }

    public long getStarTwoCount() {
        return starTwoCount;
    }

    public void setStarTwoCount(long starTwoCount) {
        this.starTwoCount = starTwoCount;
    }

    public long getStarThreeCount() {
        return starThreeCount;
    }

    public void setStarThreeCount(long starThreeCount) {
        this.starThreeCount = starThreeCount;
    }

    public long getStarFourCount() {
        return starFourCount;
    }

    public void setStarFourCount(long starFourCount) {
        this.starFourCount = starFourCount;
    }

    public long getStarFiveCount() {
        return starFiveCount;
    }

    public void setStarFiveCount(long starFiveCount) {
        this.starFiveCount = starFiveCount;
    }

    public long getRateBook() {
        return rateBook;
    }

    public void setRateBook(long rateBook) {
        this.rateBook = rateBook;
    }
}
