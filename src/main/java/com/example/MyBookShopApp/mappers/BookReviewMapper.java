package com.example.MyBookShopApp.mappers;

import com.example.MyBookShopApp.data.BookRating;
import com.example.MyBookShopApp.data.book.review.BookReviewEntity;
import com.example.MyBookShopApp.dtos.BookRatingDto;
import com.example.MyBookShopApp.dtos.BookReviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookReviewMapper {

    BookReviewMapper INSTANCE = Mappers.getMapper(BookReviewMapper.class);

    List<BookReviewDto> bookReviewEntityListToBookReviewDto(List<BookReviewEntity> bookReviewEntityList);
}
