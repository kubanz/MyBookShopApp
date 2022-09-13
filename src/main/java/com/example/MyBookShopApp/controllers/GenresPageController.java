package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.BookService;
import com.example.MyBookShopApp.data.GenreService;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.dtos.BooksPageDto;
import com.example.MyBookShopApp.dtos.GenreDto;
import com.example.MyBookShopApp.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Controller
public class GenresPageController {

    private GenreRepository genreRepository;
    private GenreService genreService;
    private BookService bookService;

    @Autowired
    public GenresPageController(GenreRepository genreRepository, GenreService genreService, BookService bookService) {
        this.genreRepository = genreRepository;
        this.genreService = genreService;
        this.bookService = bookService;
    }

    @GetMapping("/books/genres")
    public String genrePage(Model model) {
        List<GenreEntity> genreEntities = getGenreWithoutDuplicates(0, new HashSet<>(), genreRepository.findAll());
        model.addAttribute("parentGenreList", new GenreDto(genreEntities).getGenreEntities());
//        model.addAttribute("genreList", genreRepository.findAll());
//        model.addAttribute("childGenreList", genreRepository.findAll());
        return "/genres/index";

    }

    @GetMapping(value = "/books/genres/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String getSlugPage(@PathVariable(value = "id") Integer id, Model model) {
        model.addAttribute("slugUrl", genreRepository.getSlugUrl(id));
        model.addAttribute("genreTitle", genreService.getGenreTitle(id));
        model.addAttribute("getBookOfGenre", bookService.getBooksOfGenre(id, 0, 20));
        model.addAttribute("refreshId", id);
//        System.out.println(offset + " " + limit);
        return "/genres/slug";
    }

    @GetMapping(value = "/books/genre/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BooksPageDto getSlugPage(@RequestParam("offset") Integer offset,
                                    @RequestParam("limit") Integer limit,
                                    @PathVariable(value = "id") Integer id) {
        return new BooksPageDto(bookService.getBooksOfGenre(id, offset, limit).getContent());
    }

    private List<GenreEntity> getGenreWithoutDuplicates(int page, Set<Integer> visitedGenre, List<GenreEntity> genreEntities) {
        page++;
        Iterator<GenreEntity> itr = genreEntities.iterator();

        while (itr.hasNext()) {
            GenreEntity genreEntity = itr.next();
            boolean addedToVisitedGenre = visitedGenre.add(genreEntity.getId());
            if (!addedToVisitedGenre) {
                itr.remove();
                if (page != 1)
                    return genreEntities;
            }
            if (addedToVisitedGenre && !genreEntity.getChildren().isEmpty())
                getGenreWithoutDuplicates(page, visitedGenre, genreEntity.getChildren());
        }

        return genreEntities;
    }
}
