package com.creditsuisse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name = "borrowed_book_isbn", nullable = false)
    @NotNull(message = "borrowed book isbn is mandatory")
    private int borrowedBookISBN;

    @Column(name = "borrower_id", nullable = false)
    @NotNull(message = "borrower id is mandatory")
    private int borrowerId;

}
