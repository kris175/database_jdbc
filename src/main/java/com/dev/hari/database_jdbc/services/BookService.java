package com.dev.hari.database_jdbc.services;

import com.dev.hari.database_jdbc.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {
    public BookEntity createBook(String isbn, BookEntity book);

    List<BookEntity> findAll();

    Optional<BookEntity> findByIsbn(String isbn);
}
