package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.ResourceStorage;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.service.BookRatingService;
import com.example.MyBookShopApp.service.BookReviewService;
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
    private final BookReviewService bookReviewService;
    private final ResourceStorage storage;

    @Autowired
    public BooksController(BookRepository bookRepository, BookRatingService bookRatingService, BookReviewService bookReviewService, ResourceStorage storage) {
        this.bookRepository = bookRepository;
        this.bookRatingService = bookRatingService;
        this.bookReviewService = bookReviewService;
        this.storage = storage;
    }


    @RequestMapping(value = "/bookReview", method = RequestMethod.POST)
    @ResponseBody
    public String bookReviewSave(@RequestParam(value = "bookId", required = false) String slug,
                                 @RequestParam(value = "text", required = false) String text){
        System.out.println(text + " " + slug);
        bookReviewService.saveBookReview(text, slug);
        return "redirect:/books/" + slug;
    }

    @GetMapping("/{slug}")
    public String bookPage(@PathVariable("slug") String slug, Model model) {
        int userId = 48;

        boolean authStatus = false;

        Book book = bookRepository.findBookBySlug(slug);
        model.addAttribute("slugBook", book);
//        model.addAttribute("rating", "rate");
        model.addAttribute("rating", bookRatingService.getBookRating(slug, userId));
//        model.addAttribute("bookReview", bookReviewService.getBookReview(slug));
        model.addAttribute("bookReview", bookReviewService.getBookReview(slug));
        if(authStatus){
            return "/books/slugmy";
        }
        else {
            return "/books/slug";
        }
    }

/*    @PostMapping("/bookReview")
    public String bookReview(){

    }*/

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
