package com.creditsuisse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "isbn", updatable = false, nullable = false)
    private int isbn;

    @Column(nullable = false)
    @NotNull(message = "Title is mandatory")
    private String title;
    private BigDecimal cost;

    private int quantityAvailable;
}