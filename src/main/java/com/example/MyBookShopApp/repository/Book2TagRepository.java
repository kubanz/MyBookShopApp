package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.Book2Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Book2TagRepository extends JpaRepository<Book2Tag, Integer> {

    @Query(value = "select * from (\n" +
            "\tselect id, tag_description,\n" +
            "\tcase when countbook between (max(countbook) over () - ((max(countbook) over () - min(countbook) over()) / 5)) and max(countbook) over ()\n" +
            "\tthen 4 \n" +
            "\twhen countbook between (max(countbook) over () - ((max(countbook) over () - min(countbook) over()) / 5)*2)\n" +
            "\tand (max(countbook) over () - ((max(countbook) over () - min(countbook) over()) / 5)) then 3  \n" +
            "\twhen countbook between (max(countbook) over () - ((max(countbook) over () - min(countbook) over()) / 5)*3)\n" +
            "\tand (max(countbook) over () - ((max(countbook) over () - min(countbook) over()) / 5)*2) then 2  \n" +
            "\twhen countbook between (max(countbook) over () - ((max(countbook) over () - min(countbook) over()) / 5)*4)\n" +
            "    and (max(countbook) over () - ((max(countbook) over () - min(countbook) over()) / 5)*3) then 1 \n" +
            "\twhen countbook between min(countbook) over()\tand (max(countbook) over () - ((max(countbook) over () - min(countbook) over()) / 5)*4) then 0\n" +
            "\tend tag_rating\n" +
            "\tfrom (\n" +
            "\t\tselect tag.id, tag.tag_description\n" +
            "\t\t\t, count(b.id) countBook\n" +
            "\t\tFROM public.book2tag tag\n" +
            "\t\tinner join public.books b on b.tag_id = tag.id \n" +
            "\t\tgroup by tag.id, tag.tag_description\n" +
            "\t) t\n" +
            "\tgroup by id, countbook, tag_description\n" +
            "\t) t1 ", nativeQuery = true)
     List<Book2Tag> getTagByRating();
}
