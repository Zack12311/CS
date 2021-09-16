package com.creditsuisse.repository;

import com.creditsuisse.model.Borrow;

import java.util.List;
import java.util.Optional;

public interface CustomLibraryRepository {

    Optional<String> borrowBook(Borrow borrow);

    Optional<String> returnBook(Borrow borrow);

    Optional<String> returnBooks(List<Borrow> borrow);

    int findAmountOfBooksAvailable();

    int countByBorrowerId(int borrowerId);
}
