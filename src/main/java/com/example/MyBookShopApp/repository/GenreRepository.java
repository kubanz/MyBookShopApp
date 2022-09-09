package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.dtos.GenreUrlDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {

    List<GenreEntity> findByParentIdIsNull();

    @Query(value = "with recursive genre_recursive as(\n" +
            "\tselect id, \"name\", slug, parent_id from public.genre\n" +
            "\twhere id = ?1\n" +
            "\tunion\n" +
            "\tselect g.id, g.\"name\", g.slug, g.parent_id\n" +
            "\t\tfrom public.genre g\n" +
            "\tinner join genre_recursive s on s.parent_id = g.id\n" +
            ")\n" +
            "select * from genre_recursive order by 1", nativeQuery = true)
   List<GenreEntity> getSlugUrl(Integer id);

    GenreEntity getGenreEntityByIdIs(int genreId);
}
