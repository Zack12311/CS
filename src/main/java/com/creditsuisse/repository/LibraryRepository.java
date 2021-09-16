package com.creditsuisse.repository;

import com.creditsuisse.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<Borrow, Integer>, CustomLibraryRepository {

    List<Borrow> findByBorrowedBookISBN(int isbn);

    List<Borrow> findByBorrowerId(int borrowerId);

}
