package com.creditsuisse.repository;

import com.creditsuisse.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByTitleLike(String title);

    List<Book> findByTitle(String title);

    void deleteByIsbn(int isbn);

    @Query(value = "SELECT * FROM book b WHERE b.isbn = ?1", nativeQuery = true)
    Book findByISBN(@PathVariable Integer isbn);

}
