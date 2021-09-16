package com.creditsuisse.repository;

import com.creditsuisse.model.User;

import java.util.List;

public interface CustomUserRepository {

    List<User> findAllUsers();
}
