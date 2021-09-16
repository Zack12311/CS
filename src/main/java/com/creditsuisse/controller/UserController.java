package com.creditsuisse.controller;

import com.creditsuisse.model.Borrow;
import com.creditsuisse.model.User;
import com.creditsuisse.repository.LibraryRepository;
import com.creditsuisse.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@Validated
@RequestMapping(path = "/users/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @ResponseBody
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.of(Optional.of(userRepository.findAllUsers()));
    }

    @ResponseBody
    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {

        log.debug("Getting user with id: {}", id);
        return ResponseEntity.of(userRepository.findById(id));
    }

    @PutMapping("/")
    @Transactional
    public ResponseEntity<Void> saveUser(@Valid @RequestBody User user) {

        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/")
    @Transactional
    public ResponseEntity<Void> deleteUser(@Valid @RequestBody User user) {

        log.debug("returning all books for user with id: {}", user.getId());
        List<Borrow> borrows = libraryRepository.findByBorrowerId(user.getId());
        libraryRepository.returnBooks(borrows);

        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> deleteUserById(@PathVariable int id) {

        List<Borrow> borrows = libraryRepository.findByBorrowerId(id);
        libraryRepository.returnBooks(borrows);

        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
