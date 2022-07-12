package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksData(){
        return bookRepository.findAll();
    }

    /*private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBooksData(){

        String sql = "SELECT * FROM books";

        List<Book> books = jdbcTemplate.query(sql, (ResultSet rs, int rowNum)->{
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(getAuthorByAuthorID(rs.getInt("author_id")));
            book.setTitle(rs.getString("title"));
            book.setPrice_old(rs.getString("price_old"));
            book.setPrice(rs.getString("price"));
            return book;
        });
        return new ArrayList<>(books);
    }

    private String getAuthorByAuthorID(int author_id) {
        List<Author> authors = jdbcTemplate.query("select * from authors where authors.id =" +author_id, (ResultSet rs,
            int rowNum)->{
            Author author = new Author();
            author.setId(rs.getInt("id"));
            author.setFirst_name(rs.getString("first_name"));
            author.setLast_name(rs.getString("last_name"));
            return author;
        });
           return authors.get(0).toString();
    }*/
}
