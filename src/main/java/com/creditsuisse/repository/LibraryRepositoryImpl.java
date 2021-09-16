package com.creditsuisse.repository;

import com.creditsuisse.model.Book;
import com.creditsuisse.model.Borrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class LibraryRepositoryImpl implements CustomLibraryRepository {

    @Lazy
    @Autowired
    private BookRepository bookRepository;

    @Lazy
    @Autowired
    private LibraryRepository libraryRepository;

    @Override
    public Optional<String> borrowBook(Borrow borrow) {

        final int borrowedBookISBN = borrow.getBorrowedBookISBN();

        Book book = bookRepository.findByISBN(borrowedBookISBN);

        if (book == null) {
            return Optional.of("Book with isbn: {} does not exist.");
        }

        if (book.getQuantityAvailable() == 0) {
            return Optional.of("Available quantity is 0. No books to borrow.");
        }

        List<Borrow> listOfBorrowedBooks = libraryRepository.findByBorrowedBookISBN(borrowedBookISBN);

        if (listOfBorrowedBooks.size() <= book.getQuantityAvailable()) {
            return Optional.of("All books are already borrowed");
        }

        Borrow borrowing = Borrow.builder()
                .borrowedBookISBN(borrowedBookISBN)
                .borrowerId(borrow.getBorrowerId())
                .build();

        libraryRepository.save(borrowing);

        return Optional.of("Borrowed");
    }

    @Override
    @Transactional
    public Optional<String> returnBook(Borrow borrow) {

        Book book = bookRepository.findByISBN(borrow.getBorrowedBookISBN());

        if (book == null) {
            return Optional.of("Book with isbn: {} does not exist.");
        }

        List<Borrow> listOfBorrowedBooksForUser = libraryRepository.findByBorrowedBookISBN(borrow.getBorrowedBookISBN());

        Borrow persistedBorrow = listOfBorrowedBooksForUser.stream()
                .filter(p -> p.getBorrowerId() == borrow.getBorrowerId())
                .findFirst()
                .orElse(null);

        if (persistedBorrow == null) {
            return Optional.of(String.format("User with id: %s, do not have borrowed book with isbn: %s", borrow.getBorrowerId(), borrow.getBorrowedBookISBN()));
        }

        libraryRepository.delete(persistedBorrow);

        return Optional.of("Book returned.");
    }

    @Override
    @Transactional
    public Optional<String> returnBooks(List<Borrow> borrows) {
        borrows.forEach(this::returnBook);
        return Optional.of("All books has been returned");
    }

    @Override
    public int findAmountOfBooksAvailable() {

        List<Book> books = bookRepository.findAll();

        if (books.size() == 0) {
            return 0;
        }

        Integer sumOfBooks = books.stream().map(Book::getQuantityAvailable).collect(Collectors.toList()).stream()
                .reduce(0, Integer::sum);

        return sumOfBooks - libraryRepository.findAll().size();
    }

    /*
    "Bonus points for advanced search to find total books borrowed by a specific patron."
    total in life time or total currently ?
     */
    @Override
    public int countByBorrowerId(int userId) {
        return libraryRepository.countByBorrowerId(userId);
    }

}
