package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.dtos.BooksPageDto;
import com.example.MyBookShopApp.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private GenreRepository repository;

    @Autowired
    public GenreService(GenreRepository repository) {
        this.repository = repository;
    }
//
//    public Map<String, List<GenreEntity>> getGenreData(){
//        List<GenreEntity> authors = repository.findAll();
//        return authors.stream().collect(Collectors.groupingBy((GenreEntity a) ->{return a.getName().substring(0,1);}));
//    }

    public String getGenreTitle(int genreId){
        GenreEntity genreEntity = repository.getGenreEntityByIdIs(genreId);
        return genreEntity.getName();
    }
}
