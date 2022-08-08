package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.repository.Book2TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Book2TagService {

    private Book2TagRepository book2TagRepository;

    @Autowired
    public Book2TagService(Book2TagRepository book2TagRepository) {
        this.book2TagRepository = book2TagRepository;
    }

    public List<Book2Tag> getAllTag(){
        return book2TagRepository.getTagByRating();
    }
}
