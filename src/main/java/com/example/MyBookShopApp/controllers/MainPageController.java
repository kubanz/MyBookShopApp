package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.*;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
public class MainPageController {

    private final BookService bookService;

    private final BooksRatingAndPopularityService popularityService;

    private final Book2TagService book2TagService;

    private final GenreRepository genreRepository;

    @Autowired
    public MainPageController(BookService bookService, BooksRatingAndPopularityService popularityService, Book2TagService book2TagService, GenreRepository genreRepository) {
        this.bookService = bookService;
        this.popularityService = popularityService;
        this.book2TagService = book2TagService;
        this.genreRepository = genreRepository;
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
    public String genrePage(Model model){
        List<GenreEntity> genreEntities = getGenreWithoutDuplicates(0, new HashSet<>(), genreRepository.findAll());
        model.addAttribute("parentGenreList", genreEntities);
//        model.addAttribute("genreList", genreRepository.findAll());
//        model.addAttribute("childGenreList", genreRepository.findAll());
        return "/genres/index";

    }

    private List<GenreEntity> getGenreWithoutDuplicates(int page, Set<Integer> visitedGenre, List<GenreEntity> genreEntities){
        page++;
        Iterator<GenreEntity> itr = genreEntities.iterator();

        while (itr.hasNext()){
            GenreEntity genreEntity = itr.next();
            boolean addedToVisitedGenre = visitedGenre.add(genreEntity.getId());
            if(!addedToVisitedGenre){
                itr.remove();
                if(page !=1)
                    return genreEntities;
            }
            if(addedToVisitedGenre && !genreEntity.getChildren().isEmpty())
                getGenreWithoutDuplicates(page, visitedGenre, genreEntity.getChildren());
        }

        return genreEntities;
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
                                            @RequestParam("limit") Integer limit){
        return  new BooksPageDto(bookService.getBooksForTagPage(tagID, offset, limit).getContent());
    }


}
