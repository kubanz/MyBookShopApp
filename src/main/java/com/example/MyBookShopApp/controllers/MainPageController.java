package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.*;
import com.example.MyBookShopApp.dtos.BooksPageDto;
import com.example.MyBookShopApp.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;

    private final BooksRatingAndPopularityService popularityService;

    private final Book2TagService book2TagService;

    @Autowired
    public MainPageController(BookService bookService, BooksRatingAndPopularityService popularityService,
                              Book2TagService book2TagService, GenreRepository genreRepository,
                              GenreService genreService) {
        this.bookService = bookService;
        this.popularityService = popularityService;
        this.book2TagService = book2TagService;
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.getPageOfRecommendedBooks(0, 6).getContent();
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks() {
        return popularityService.getPageOfPopularBooks(0, 6).getContent();
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }


    @GetMapping("/postponed")
    public String postponedPage() {
        return "/postponed";
    }

    @GetMapping("/cart")
    public String cartPage() {
        return "/cart";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "/about";
    }

    @GetMapping("/faq")
    public String faqPage() {
        return "/faq";
    }

    @GetMapping("/contacts")
    public String contactsPage() {
        return "/contacts";
    }


    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksPageDto getBooksPage(@RequestParam("offset") Integer offset,
                                     @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }


    @ModelAttribute("popularPageBooks")
    public List<Book> popularPageBooks() {
        return popularityService.getPageOfPopularBooks(0, 20).getContent();
    }

    @GetMapping(value = "/books/popular", produces = MediaType.TEXT_HTML_VALUE)
    public String popularBookPage() {
        return "/books/popular";
    }

    @GetMapping(value = "/books/popular", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BooksPageDto getPopularPage(@RequestParam("offset") Integer offset,
                                       @RequestParam("limit") Integer limit) {
        return new BooksPageDto(popularityService.getPageOfPopularBooks(offset, limit).getContent());
    }

    @ModelAttribute("allBookTag")
    public List<Book2Tag> allBookTag() {
        return book2TagService.getAllTag();
    }

    @GetMapping(value = "/books/tag/{tagID}", produces = MediaType.TEXT_HTML_VALUE)
    public String getBookTagPage(@PathVariable(value = "tagID", required = false) String tagID, Model model) {
        model.addAttribute("tagPage", bookService.getBooksForTagPage(tagID, 0, 20));
        model.addAttribute("refreshID", tagID);
        model.addAttribute("tagTitle", book2TagService.getTagByID(Integer.valueOf(tagID)));
        return "/tags/index";
    }

    @GetMapping(value = "/books/tag/{tagID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BooksPageDto getBooksForPageTag(@PathVariable(value = "tagID", required = false) String tagID,/*@RequestParam("refreshid") String tagID,*/
                                           @RequestParam("offset") Integer offset,
                                           @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getBooksForTagPage(tagID, offset, limit).getContent());
    }


}
