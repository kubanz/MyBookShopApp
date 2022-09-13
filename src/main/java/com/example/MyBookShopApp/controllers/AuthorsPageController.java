package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.data.AuthorService;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookService;
import com.example.MyBookShopApp.dtos.BooksPageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Api(description = "authors data")
public class AuthorsPageController {


    private final AuthorService authorService;

    private final BookService bookService;

    @Autowired
    public AuthorsPageController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @ApiOperation("method to get map of author")
    @ModelAttribute("authorsMap")
    public Map<String, List<Author>> authorsMap() {
        Map<String, List<Author>> authorsMap = authorService.getAuthorsData();
        return authorsMap;
    }

    @GetMapping("/authors")
    public String authorPage() {
        return "/authors/index";
    }

    @GetMapping(value = "/authors/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String authorBiographyPage(@PathVariable Integer id, Model model) {
        model.addAttribute("author", authorService.getAuthor(id));
        model.addAttribute("authorBooks",
                bookService.getBooksByAuthorID(id, 0, 20).getContent());
        model.addAttribute("authorId", id);
        model.addAttribute("bookCount", bookService.getBooksCountByAuthorID(id));
        System.out.println(id);
        return "/authors/slug";
    }

    @GetMapping(value = "/books/author/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BooksPageDto getSlugPage(@RequestParam("offset") Integer offset,
                                    @RequestParam("limit") Integer limit,
                                    @PathVariable(value = "id") Integer id) {
        return new BooksPageDto(bookService.getBooksByAuthorID(id, offset, limit).getContent());
    }


    @GetMapping(value = "/books/author/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String bookAuthorPage(@PathVariable Integer id, Model model) {
        model.addAttribute("author", authorService.getAuthor(id));
        model.addAttribute("authorBooks",
                bookService.getBooksByAuthorID(id, 0, 20).getContent());
        model.addAttribute("authorId", id);
        System.out.println(id);
        String s = "/books/author";
        return s;
    }

//    @ApiOperation("method to get map of author")
//    @GetMapping("/api/authors")
//    @ResponseBody
//    public Map<String, List<Author>> authors(){
//        return authorService.getAuthorsData();
//    }

}
