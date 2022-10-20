package com.example.MyBookShopApp.service.impl;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.data.book.links.Book2UserTypeEntity;
import com.example.MyBookShopApp.dtos.BookDto;
import com.example.MyBookShopApp.dtos.BookIdDto;
import com.example.MyBookShopApp.mappers.BookMapper;
import com.example.MyBookShopApp.repository.Book2UserRepository;
import com.example.MyBookShopApp.repository.Book2UserTypeRepository;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.service.ChangeBookStatusService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChangeBookStatusServiceImpl implements ChangeBookStatusService {

    private final BookRepository bookRepository;
    private final Book2UserRepository book2UserRepository;
    private final Book2UserTypeRepository book2UserTypeRepository;
    private BookMapper bookMapper = BookMapper.INSTANCE;

    public ChangeBookStatusServiceImpl(BookRepository bookRepository, Book2UserRepository book2UserRepository, Book2UserTypeRepository book2UserTypeRepository) {
        this.bookRepository = bookRepository;
        this.book2UserRepository = book2UserRepository;
        this.book2UserTypeRepository = book2UserTypeRepository;
    }

//    private BookMapper bookMapper = BookMapper.INSTANCE;

    @Override
    public void addToKept(String slug, String typeID) {
        Book2UserEntity book2User = new Book2UserEntity();
        Book book = bookRepository.findBookBySlug(slug);
        Book2UserTypeEntity type = book2UserTypeRepository.findBook2UserTypeEntitiesByCodeContaining(typeID);
//        System.out.println("Test");
//        book2User.setId(1001);
        book2User.setUserId(112);
        book2User.setTypeId(type.getId());
        book2User.setTime(LocalDateTime.now());
        book2User.setBookId(book.getId());
        book2UserRepository.save(book2User);
//        BookIdDto bookIdDto = bookMapper.bookToBookIdDto(book);
    }

    @Override
    public void deletePostponedBook(String slug) {
        Book book =  bookRepository.findBookBySlug(slug);
        BookIdDto bookIdDto = bookMapper.bookToBookIdDto(book);
        book2UserRepository.deleteBook2UserEntityByBookIdAndUserIdIs(bookIdDto.getId(), 112);
    }

    @Override
    public List<BookDto> getPostponedBook(){
        List<Book> book = bookRepository.getPostponedBookByUser(112, 1);
        List<BookDto> bookDto = bookMapper.bookListToBookDtoList(book);
        return bookDto;
    }


}
