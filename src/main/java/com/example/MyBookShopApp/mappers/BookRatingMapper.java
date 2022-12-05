package com.example.MyBookShopApp.mappers;

import com.example.MyBookShopApp.data.BookRating;
import com.example.MyBookShopApp.dtos.BookRatingDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookRatingMapper {

    BookRatingMapper INSTANCE = Mappers.getMapper(BookRatingMapper.class);

    List<BookRatingDto> bookRatingListToBookRatingListDto(List<BookRating> bookRatingList);
}
