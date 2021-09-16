package com.creditsuisse.controller;

import com.creditsuisse.model.Book;
import com.creditsuisse.model.Borrow;
import com.creditsuisse.repository.BookRepository;
import com.creditsuisse.repository.LibraryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@Validated
@RequestMapping(path = "/books/")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryRepository libraryRepository;


    @ResponseBody
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(@PathVariable int isbn) {

        log.debug("Getting books");
        return ResponseEntity.of(Optional.of(bookRepository.findAll()));
    }

    @ResponseBody
    @GetMapping("{id}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable int isbn) {

        log.debug("Getting book with isbn: {}", isbn);
        return ResponseEntity.of(Optional.of(bookRepository.findByISBN(isbn)));
    }

    @ResponseBody
    @GetMapping("{title}")
    public ResponseEntity<List<Book>> getBookByTitle(@PathParam(value = "title") @NotNull String title) {

        log.debug("Getting books with title: {}", title);

        if (title.contains("%")) {
            return ResponseEntity.of(Optional.of(bookRepository.findByTitleLike(title)));
        } else {
            return ResponseEntity.of(Optional.of(bookRepository.findByTitle(title)));
        }
    }

    @PutMapping("/")
    @Transactional
    public ResponseEntity<Void> saveBook(@Valid @RequestBody Book book) {

        bookRepository.save(book);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{id}")
    @Transactional
    public ResponseEntity<Void> deleteBookByIsbn(@PathVariable int isbn) {

        List<Borrow> borrows = libraryRepository.findByBorrowedBookISBN(isbn);
        libraryRepository.deleteAll(borrows);

        bookRepository.deleteByIsbn(isbn);
        return ResponseEntity.noContent().build();
    }

}
