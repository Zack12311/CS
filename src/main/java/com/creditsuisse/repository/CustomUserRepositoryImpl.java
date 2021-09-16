package com.creditsuisse.repository;

import com.creditsuisse.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

public class CustomUserRepositoryImpl implements CustomUserRepository {

    @Lazy
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAllUsers() {

        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        return users;
    }
}
