package com.creditsuisse.controller;

import com.creditsuisse.model.Borrow;
import com.creditsuisse.repository.LibraryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.Optional;

@Slf4j
@RestController
@Validated
@RequestMapping(path = "/library/")
public class LibraryController {

    @Autowired
    private LibraryRepository libraryRepository;

    @ResponseBody
    @PutMapping("borrow/")
    public ResponseEntity<String> borrowBook(@Valid @RequestBody final Borrow borrowRequest) {

        log.debug(String.format("Request to borrow book with book with isbn: %s for user with id: %s.",
                borrowRequest.getBorrowedBookISBN(), borrowRequest.getBorrowerId()));

        return ResponseEntity.of(libraryRepository.borrowBook(borrowRequest));
    }

    @ResponseBody
    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@Valid @RequestBody final Borrow borrow) {

        return ResponseEntity.of(libraryRepository.returnBook(borrow));
    }

    @ResponseBody
    @GetMapping("/available")
    public ResponseEntity<Integer> findAmountOfBooksAvailable() {

        return ResponseEntity.of(Optional.of(libraryRepository.findAmountOfBooksAvailable()));
    }

    @ResponseBody
    @GetMapping("/user/{id}")
    public ResponseEntity<Integer> findAmountOfBorrowedBooksByUser(@PathParam(value = "userId") int userId) {

        return ResponseEntity.of(Optional.of(libraryRepository.countByBorrowerId(userId)));
    }

}