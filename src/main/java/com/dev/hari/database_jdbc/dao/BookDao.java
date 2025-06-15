package com.dev.hari.database_jdbc.dao;

import com.dev.hari.database_jdbc.domain.Book;

import java.util.Optional;

public interface BookDao {
    void create(Book book);

    Optional<Book> findOne(String isbn);
}
