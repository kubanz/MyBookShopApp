package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRatingRepository extends JpaRepository<BookRating, Integer> {

    List<BookRating> findAllByBook_Slug(String bookSlug);

    BookRating findAllByBook_SlugAndUserID(String bookSlug, Integer userId);

}
