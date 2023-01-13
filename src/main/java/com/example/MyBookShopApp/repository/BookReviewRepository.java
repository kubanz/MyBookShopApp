package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.book.review.BookReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReviewEntity, Integer> {

    List<BookReviewEntity> findAllByBook_Slug(String slug);

//    @Query(value = "select new com.example.MyBookShopApp.dtos.BookReviewDto(review.book, review.userId, review.time, review.text) " +
//            "from BookReviewEntity review" +
//            " inner join review.book book inner join review.userId " +
//            " where book.slug = :slug")
//    Optional<BookReviewDto> findRateAllByBook_Slug(@Param("slug") String slug);
}
