package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {

    List<GenreEntity> findByParentIdIsNull();
}
