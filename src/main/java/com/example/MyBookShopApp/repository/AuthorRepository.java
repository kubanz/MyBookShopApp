package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.Author;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Author getAuthorByIdIs(Integer authorId);
}
