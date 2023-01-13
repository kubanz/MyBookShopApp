package com.example.MyBookShopApp.service.impl;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.book.review.BookReviewEntity;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.dtos.BookReviewDto;
import com.example.MyBookShopApp.mappers.BookReviewMapper;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.repository.BookReviewRepository;
import com.example.MyBookShopApp.repository.UserEntityRepository;
import com.example.MyBookShopApp.service.BookReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookReviewServiceImpl implements BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final BookRepository bookRepository;
    private final UserEntityRepository userEntityRepository;
    private BookReviewMapper bookReviewMapper = BookReviewMapper.INSTANCE;

    public BookReviewServiceImpl(BookReviewRepository bookReviewRepository, BookRepository bookRepository, UserEntityRepository userEntityRepository) {
        this.bookReviewRepository = bookReviewRepository;
        this.bookRepository = bookRepository;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public List<BookReviewDto> getBookReview(String slug) {
        List<BookReviewEntity> bookReviewEntityList = bookReviewRepository.findAllByBook_Slug(slug);
        List<BookReviewDto> bookReviewDtoList = bookReviewMapper.bookReviewEntityListToBookReviewDto(bookReviewEntityList);
//        Optional<BookReviewDto> bookReviewDtoList =bookReviewRepository.findRateAllByBook_Slug(slug);
        return bookReviewDtoList;
    }

    public void saveBookReview(String text, String slug){
        Book book = bookRepository.findBookBySlug(slug);
        UserEntity userEntity = userEntityRepository.getById(48);
        BookReviewEntity bookReviewEntity = new BookReviewEntity(book, userEntity, LocalDateTime.now(), text);
        bookReviewRepository.save(bookReviewEntity);
    }
}
