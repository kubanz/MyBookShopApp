package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewLikeRepository extends JpaRepository<BookReviewLikeEntity, Integer> {

}
