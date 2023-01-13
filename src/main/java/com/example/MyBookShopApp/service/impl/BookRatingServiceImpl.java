package com.example.MyBookShopApp.service.impl;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookRating;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.dtos.BookRatingDto;
import com.example.MyBookShopApp.dtos.RatingPageDto;
import com.example.MyBookShopApp.mappers.BookRatingMapper;
import com.example.MyBookShopApp.repository.BookRatingRepository;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.repository.UserEntityRepository;
import com.example.MyBookShopApp.service.BookRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;

@Service
public class BookRatingServiceImpl implements BookRatingService {

    private final BookRatingRepository bookRatingRepository;
    private final BookRepository bookRepository;
    private final UserEntityRepository userEntityRepository;
    private BookRatingMapper bookRatingMapper = BookRatingMapper.INSTANCE;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public BookRatingServiceImpl(BookRatingRepository bookRatingRepository, BookRepository bookRepository,
                                 UserEntityRepository userEntityRepository) {
        this.bookRatingRepository = bookRatingRepository;
        this.bookRepository = bookRepository;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public RatingPageDto getBookRating(String slug, int userId) {
        List<BookRating> bookRating = bookRatingRepository.findAllByBook_Slug(slug);
        List<BookRatingDto> bookRatingDtos = bookRatingMapper.bookRatingListToBookRatingListDto(bookRating);
        Double rating = bookRatingDtos.stream().mapToInt(BookRatingDto::getRating).average().getAsDouble();
        long ratingCount = bookRatingDtos.stream().mapToInt(BookRatingDto::getRating).count();
        long starOne = bookRatingDtos.stream().filter(b -> b.getRating() == 1).count();
        long starTwo = bookRatingDtos.stream().filter(b -> b.getRating() == 2).count();
        long starThree = bookRatingDtos.stream().filter(b -> b.getRating() == 3).count();
        long starFour = bookRatingDtos.stream().filter(b -> b.getRating() == 4).count();
        long starFive = bookRatingDtos.stream().filter(b -> b.getRating() == 5).count();
        BookRating rateBook = bookRatingRepository.findAllByBook_SlugAndAndUser_Id(slug, userId);
        long rateUserStar;
        if(Objects.isNull(rateBook)){
            rateUserStar = 5; //default value
        } else{
            rateUserStar = rateBook.getRating();
        }
        Integer ratingStart = Math.toIntExact(Math.round(rating));
        RatingPageDto ratingPageDto = new RatingPageDto(ratingCount,ratingStart,starOne,
                starTwo,starThree, starFour, starFive, rateUserStar);
        return ratingPageDto;
    }

    @Override
    public void saveUpdateBookRate(String slug, int userId, int value){

        BookRating bookRating = bookRatingRepository.findAllByBook_SlugAndAndUser_Id(slug, userId);
        UserEntity userEntity = userEntityRepository.getById(userId);
        if(Objects.isNull(bookRating)) {
            Book book = bookRepository.findBookBySlug(slug);
            BookRating rating = new BookRating(value, book, userEntity);
            bookRatingRepository.save(rating);
        }else {
            bookRating.setRating(value);
            bookRatingRepository.save(bookRating);
        }
    }
}
