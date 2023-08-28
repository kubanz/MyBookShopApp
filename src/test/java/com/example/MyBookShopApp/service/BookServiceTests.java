package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookRating;
import com.example.MyBookShopApp.data.book.review.BookReviewEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.repository.BookRatingRepository;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.repository.BookReviewLikeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookServiceTests {

    private final BookRepository bookRepository;

    private final BookRatingRepository rateRepository;

    private final BookReviewLikeRepository bookReviewLikeRepository;


    @Autowired
    BookServiceTests(BookRepository bookRepository, BookRatingRepository rateRepository,
                     BookReviewLikeRepository bookReviewLikeRepository) {
        this.bookRepository = bookRepository;
        this.rateRepository = rateRepository;
        this.bookReviewLikeRepository = bookReviewLikeRepository;
    }



    @Test
    void getPageOfRecommendedBooks() {
        final String book1 = "Flood";
        final String book2 = "Il fiore dai petali d'acciaio";

        Pageable nextPage = PageRequest.of(0, 5);
        List<Book> books = bookRepository.findAll(nextPage).getContent();        assertEquals(book1, books.get(0).getTitle());
        assertEquals(book2, books.get(1).getTitle());

    }

    @Test
    void getReviews() {
        BookReviewLikeEntity bookReviewLike = new BookReviewLikeEntity();
        bookReviewLike.setValue((short) 5);
        bookReviewLike.setTime(LocalDateTime.now());
        bookReviewLikeRepository.save(bookReviewLike);

        List<BookReviewLikeEntity> allLikes = bookReviewLikeRepository.findAll();

        Optional<BookReviewLikeEntity> maxLike = allLikes.stream()
                .max(Comparator.comparingInt(BookReviewLikeEntity::getValue));
        assertEquals(bookReviewLike.getId(), maxLike.get().getId());

        bookReviewLikeRepository.delete(bookReviewLike);
    }
}