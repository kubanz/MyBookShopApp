package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.ResourceStorage;
import com.example.MyBookShopApp.dtos.RatingPageDto;
import com.example.MyBookShopApp.repository.BookRatingRepository;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.service.BookRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookRepository bookRepository;
    private final BookRatingService bookRatingService;
    private final ResourceStorage storage;

    @Autowired
    public BooksController(BookRepository bookRepository, BookRatingService bookRatingService, ResourceStorage storage) {
        this.bookRepository = bookRepository;
        this.bookRatingService = bookRatingService;
        this.storage = storage;
    }

//    @ModelAttribute("rating")
//    public RatingPageDto rateBook(){
//        return bookRatingService.getBookRating(slug);
//    }

    @GetMapping("/{slug}")
    public String bookPage(@PathVariable("slug") String slug, Model model) {
        Book book = bookRepository.findBookBySlug(slug);
        model.addAttribute("slugBook", book);
        model.addAttribute("rating", bookRatingService.getBookRating(slug, 48));
        return "/books/slug";
    }

    @PostMapping("/{slug}/img/save")
    public String saveNewBookImage(@RequestParam("file")MultipartFile file,@PathVariable("slug")String slug) throws IOException {
        String savePath = storage.saveNewBookImage(file,slug);
        Book bookToUpdate = bookRepository.findBookBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookRepository.save(bookToUpdate); //save new path in db here
        return "redirect:/books/"+slug;
    }

    @GetMapping("/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash) throws IOException {

        Path path = storage.getBookFilePath(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file path: " + path);

        MediaType mediaType =storage.getBookFileMime(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file mime type: " + mediaType);

        byte[] data = storage.getBookFileByteArray(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file data len: " + data.length);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+path.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }

    @PostMapping("/rateBook")
    public String changeBookStarRating(@RequestParam("bookId")String slug, @RequestParam("value")String value){
        bookRatingService.saveUpdateBookRate(slug, 48, Integer.parseInt(value));
        System.out.println("value: " +value);
        return "redirect:/books/"+slug;
    }
}
