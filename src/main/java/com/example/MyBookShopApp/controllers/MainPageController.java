package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;

    private final BooksRatingAndPopularityService popularityService;

    private final Book2TagService book2TagService;

    @Autowired
    public MainPageController(BookService bookService, BooksRatingAndPopularityService popularityService, Book2TagService book2TagService) {
        this.bookService = bookService;
        this.popularityService = popularityService;
        this.book2TagService = book2TagService;
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks(){
        return bookService.getPageOfRecommendedBooks(0,6).getContent();
    }


//    @ModelAttribute("recentBooks")
//    public List<Book> recentBooks(){
//        return bookService.getPageOfRecentBooks(0,6).getContent();
//    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks(){
        return popularityService.getPageOfPopularBooks(0,6).getContent();
    }

    @GetMapping("/")
    public String mainPage(Model model){
        return "index";
    }


    @GetMapping("/postponed")
    public String postponedPage(){
        return "/postponed";
    }

    @GetMapping("/cart")
    public String cartPage(){
        return "/cart";
    }

    @GetMapping("/signin")
    public String signinPage(){
        return "/signin";
    }

    @GetMapping("/about")
    public String aboutPage(){
        return "/about";
    }

    @GetMapping("/faq")
    public String faqPage(){
        return "/faq";
    }

    @GetMapping("/contacts")
    public String contactsPage(){
        return "/contacts";
    }

    @GetMapping("/books/genres")
    public String genrePage(){
        return "/genres/index";
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults(){
        return new ArrayList<>();
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksPageDto getBooksPage(@RequestParam("offset") Integer offset,
                                     @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }

//    @GetMapping("/books/recent")
//    @ResponseBody
//    public BooksPageDto getRecentPage(@RequestParam("offset") Integer offset,
//                                     @RequestParam("limit") Integer limit) {
//        return new BooksPageDto(bookService.getPageOfRecentBooks(offset, limit).getContent());
//    }


    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String getSearchResult(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto,
                                  Model model) {
        model.addAttribute("searchWordDto", searchWordDto);
        model.addAttribute("searchResults",
                bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), 0, 5).getContent());
        return "/search/index";
    }

    @GetMapping("/search/page/{searchWord}")
    @ResponseBody
    public BooksPageDto getNextSearchPage(@RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit,
                                          @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto) {
        return new BooksPageDto(bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), offset, limit).getContent());
    }

    @ModelAttribute("recentBooks")
    public List<Book> recentBooks(){
        return bookService.getPageOfRecentBooks(0,6).getContent();
    }

    @ModelAttribute("recentPageBooks")
    public List<Book> recentPageBooks() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date startDate = Date.from(LocalDate.now().minusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date to = new SimpleDateFormat("dd.MM.yyyy").parse(dateFormat.format(endDate));
        Date from  =new SimpleDateFormat("dd.MM.yyyy").parse(dateFormat.format(startDate));
        return bookService.getPageOfRecent(0,20,  from, to).getContent();
    }

    @GetMapping(value = "/books/recent", produces = MediaType.TEXT_HTML_VALUE)
    public String recentBookPage() {
        return "/books/recent";
    }

    @GetMapping(value = "/books/recent", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BooksPageDto getRecentPage(@RequestParam("offset") Integer offset,
                                      @RequestParam("limit") Integer limit,
                                      @RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy")Date from,
                                      @RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy")Date to){
            return new BooksPageDto(bookService.getPageOfRecent(offset, limit, from, to).getContent());
//        return new BooksPageDto(bookService.getPageOfRecentBooks(offset, limit).getContent());
//        return new BooksPageDto(bookService.getPageOfRecent(offset, limit, from, to).getContent());
    }


    @ModelAttribute("popularPageBooks")
    public List<Book> popularPageBooks(){
          return popularityService.getPageOfPopularBooks(0,20).getContent();
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
    public List<Book2Tag> allBookTag(){
        return book2TagService.getAllTag();
    }
}
