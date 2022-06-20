package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, List<Author>> getAuthorsData(){
        String sql = "SELECT * FROM authors";

        List<Author> authors = jdbcTemplate.query(sql, (ResultSet rs, int rowNum)->{
                Author author = new Author();
                author.setId(rs.getInt("id"));
                author.setFirst_name(rs.getString("first_name"));
                author.setLast_name(rs.getString("last_name"));
                return author;
        });
        return authors.stream().collect(Collectors.groupingBy((Author a) ->{return a.getFirst_name().substring(0,1);}));
    }
}
