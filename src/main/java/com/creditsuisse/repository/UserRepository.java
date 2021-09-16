package com.creditsuisse.repository;

import com.creditsuisse.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>, CustomUserRepository {

}