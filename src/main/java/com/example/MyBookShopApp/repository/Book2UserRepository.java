package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface Book2UserRepository extends JpaRepository<Book2UserEntity, Integer> {

    @Transactional
    @Modifying
    void deleteBook2UserEntityByBookIdAndUserIdIs(@Param("bookId") int bookId, @Param("userID") int userId);
}
