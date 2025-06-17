package com.dev.hari.database_jdbc.services;

import com.dev.hari.database_jdbc.domain.entities.BookEntity;

import java.util.List;

public interface BookService {
    public BookEntity createBook(String isbn, BookEntity book);

    List<BookEntity> findAll();
}
