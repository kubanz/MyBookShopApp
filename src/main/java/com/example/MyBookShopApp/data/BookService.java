package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksData(){
        return bookRepository.findAll();
    }

    //NEW BOOK SERVICE METHODS

    public List<Book> getBooksByAuthor(String authorName){
        return bookRepository.findBooksByAuthorFirstNameContaining(authorName);
    }

    public List<Book> getBooksByTitle(String title){
        return bookRepository.findBooksByTitleContaining(title);
    }

    public List<Book> getBooksWithPriceBetween(Integer min, Integer max){
        return bookRepository.findBooksByPriceOldBetween(min, max);
    }

    public List<Book> getBooksWithPrice(Integer price){
        return bookRepository.findBooksByPriceOldIs(price);
    }

    public List<Book> getBooksWithMaxPrice(){
        return bookRepository.getBooksWithMaxDiscount();
    }

    public List<Book> getBestsellers(){
        return bookRepository.getBestsellers();
    }

    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findAll(nextPage);
    }

    public Page<Book> getPageOfRecentBooks(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit, Sort.by(Sort.Direction.DESC, "pubdate"));
        return bookRepository.findAll(nextPage);
    }

    public Page<Book> getPageOfRecent(Integer offset, Integer limit, Date from, Date to){
        Pageable nextPage = PageRequest.of(offset,limit, Sort.by(Sort.Direction.DESC, "pubdate"));
        if (null == from){
            return bookRepository.findAll(nextPage);
        }else {
            return bookRepository.findBookByPubdateBetween(from, to, nextPage);
        }
    }


//    public Page<Book> getPageOfPopularBooks(Integer offset, Integer limit){
//        Pageable nextPage = PageRequest.of(offset,limit);
//        return bookRepository.findAll(nextPage);
//    }

    public Page<Book> getPageOfSearchResultBooks(String searchWord, Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBookByTitleContaining(searchWord, nextPage);
    }

    public Page<Book> getBooksForTagPage(String tagID, Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.getBooksByTagIDIs(Integer.valueOf(tagID), nextPage);
    }
}
