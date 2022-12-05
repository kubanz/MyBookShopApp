package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.dtos.BookDto;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.service.BookRatingService;
import com.example.MyBookShopApp.service.impl.ChangeBookStatusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Controller
@RequestMapping("/books")
public class BookshpCartController {

    private final BookRepository bookRepository;
    private final BookRatingService bookRatingService;

    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }


    @Autowired
    private ChangeBookStatusServiceImpl changeBookStatusPostponedService;

    @Autowired
    public BookshpCartController(BookRepository bookRepository, BookRatingService bookRatingService) {
        this.bookRepository = bookRepository;
        this.bookRatingService = bookRatingService;
    }

    @GetMapping("/cart")
    public String handleCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents,
                                    Model model) {
        if (cartContents == null || cartContents.equals("")) {
            model.addAttribute("isCartEmpty", true);
        } else {
            model.addAttribute("isCartEmpty", false);
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) :
                    cartContents;
            String[] cookieSlugs = cartContents.split("/");
            List<Book> booksFormCookieSlugs = bookRepository.findBookBySlugIn(cookieSlugs);
            model.addAttribute("bookCart", booksFormCookieSlugs);

        }
        return "cart";
    }

//    @ModelAttribute("postponedBook")
//    public List<BookDto> getPostponedBookAttr(){
//
//    }

    @GetMapping("/postponed")
    public String handlePostponedRequest(Model model){
        List<BookDto> books = changeBookStatusPostponedService.getPostponedBook();
//        books.stream().forEach(System.out::println);
        model.addAttribute("postponedBook", books);
        return "postponed";
    }

    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug, @CookieValue(name = "cartContents",
            required = false) String cartContents, HttpServletResponse response, Model model) {
        if (cartContents != null && !cartContents.equals("")) {
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else {
            model.addAttribute("isCartEmpty", true);
        }
        return "redirect:/books/cart";
    }

    @PostMapping("/changeBookStatus/postponed/remove/{slug}")
    public String handleRemoveBookFromPostponedRequest(@PathVariable("slug") String slug){
        changeBookStatusPostponedService.deletePostponedBook(slug);
        return "redirect:/books/postponed";
    }

    @PostMapping("/changeBookStatus/{slug}")
    @ResponseBody
    public String handleChangeBooksStatus(@PathVariable("slug") String slug,
                                          @RequestParam(value = "status", required = false) String status,
                                          @CookieValue(name = "cartContents", required = false) String cartContents,
                                          @CookieValue(name = "postponedContents", required = false) String postponedContents,
                                          HttpServletResponse response, Model model) {
        switch (status){
            case "KEPT":
                changeBookStatusPostponedService.addToKept(slug, status);
                break;
            case "CART":
                if (cartContents == null || cartContents.equals("")) {
                    Cookie cookie = new Cookie("cartContents", slug);
                    cookie.setPath("/books");
                    response.addCookie(cookie);
                    model.addAttribute("isCartEmpty", false);
                    changeBookStatusPostponedService.deletePostponedBook(slug);
                } else if (!cartContents.contains(slug)) {
                    StringJoiner stringJoiner = new StringJoiner("/");
                    stringJoiner.add(cartContents).add(slug);
                    Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
                    cookie.setPath("/books");
                    response.addCookie(cookie);
                    model.addAttribute("isCartEmpty", false);
                    changeBookStatusPostponedService.deletePostponedBook(slug);
                }

                break;
        }

        return "redirect:/books/" + slug;
    }
}
