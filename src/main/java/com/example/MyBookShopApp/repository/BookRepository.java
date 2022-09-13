package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findBooksByAuthorFirstName(String name);

    @Query("from Book")
    List<Book> customFindAllBooks();

    //NEW BOOK REST REPOSITORY

    List<Book> findBooksByAuthorFirstNameContaining(String authorsFirstName);

    List<Book> findBooksByTitleContaining(String bookTitle);

    List<Book> findBooksByPriceOldBetween(Integer min, Integer max);

    List<Book> findBooksByPriceOldIs(Integer price);

    @Query("from Book where isBestseller=1")
    List<Book> getBestsellers();

    @Query(value = "SELECT * FROM books WHERE discount = (SELECT MAX(discount) FROM books",nativeQuery = true)
    List<Book> getBooksWithMaxDiscount();

    Page<Book> findBookByTitleContaining(String bootTitle, Pageable nextPage);

    Page<Book> findBookByPubdateBetween(Date from, Date to, Pageable nextPage);

    @Query(value = "SELECT \n" +
            "\tid, description, image, is_bestseller, discount, price, pub_date, slug, title, author_id\n" +
            "\t, cast(coalesce(b2.Paid + 0.7 * b2.CART + 0.4 * KEPT, 0) as int)  as popularity, tag_id\n" +
            "FROM public.books b\n" +
            "\tleft join (select count(case when b.type_id = 1 then 1 else null end) KEPT\n" +
            "\t\t\t\t\t, count(case when b.type_id = 2 then 1 else null end) CART\n" +
            "\t\t\t\t\t, count(case when b.type_id = 3 then 1 else null end) PAID\n" +
            "\t\t\t\t\t, count(case when b.type_id = 4 then 1 else null end) ARCHIVED\n" +
            "\t\t\t\t\t, b.book_id  \n" +
            "\t\t\t\tfrom public.book2user b\n" +
            "\t\t\t\tgroup by b.book_id) b2 on b2.book_id = b.id\n" +
            "order by popularity desc", nativeQuery = true)
    Page<Book> getBooksByPopularity(Pageable nextPageable);

    Page<Book> getBooksByTagIDIs(Integer tagID, Pageable newPageable);

    @Query(value = "SELECT b.*\n" +
            "FROM public.genre g\n" +
            "inner join public.book2genre b2g on g.id = b2g.genre_id\n" +
            "inner join public.books b on b2g.book_id = b.id\n" +
            "where g.id = ?1", nativeQuery = true)
    Page<Book> getBooksByGenre(int genreId, Pageable nextPageable);

    Page<Book> getBooksByAuthorIdIs(Integer id, Pageable nextPageable);

    Integer countAllByAuthorIdIs(Integer id);
}
