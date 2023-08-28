package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookRepositoryTest {

    private final BookRepository bookRepository;

    @Autowired
    BookRepositoryTest(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Test
    void findBooksByAuthorFirstName() {
        String token = "Abbott";
        List<Book> books = bookRepository.findBooksByAuthorFirstName(token);

        assertNotNull(books);
        assertFalse(books.isEmpty());

        for (Book book: books) {
            Logger.getLogger(this.getClass().getSimpleName()).info(book.toString());
            assertThat(book.getAuthor().getFirstName()).contains(token);
        }

    }

    @Test
    void findBooksByTitleContaining() {
        String token = "Flood";
        List<Book> books = bookRepository.findBooksByTitleContaining(token);

        assertNotNull(books);
        assertFalse(books.isEmpty());

        for(Book book: books) {
            Logger.getLogger(this.getClass().getSimpleName()).info(book.toString());
            assertThat(book.getTitle()).contains(token);
        }
    }

    @Test
    void getBestsellers() {
        List<Book> books = bookRepository.getBestsellers();
        assertNotNull(books);
        assertFalse(books.isEmpty());
        assertThat(books.size()).isGreaterThan(1);
    }
}