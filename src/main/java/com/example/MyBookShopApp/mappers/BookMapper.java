package com.example.MyBookShopApp.mappers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.dtos.BookDto;
import com.example.MyBookShopApp.dtos.BookIdDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

//    Book bookDtoToBook(BookDto bookDto);
//
//    BookDto bookToBookDto(Book book);
//
//    List<Book> bookDtListToBookList(List<BookDto> bookDtos);

    List<BookDto> bookListToBookDtoList(List<Book> books);

    BookIdDto bookToBookIdDto(Book book);

}
