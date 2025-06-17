package com.dev.hari.database_jdbc.services;

import com.dev.hari.database_jdbc.domain.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    public BookEntity save(String isbn, BookEntity book);

    List<BookEntity> findAll();

    Page<BookEntity> findAll(Pageable pageable);

    Optional<BookEntity> findByIsbn(String isbn);

    boolean doesExists(String isbn);

    BookEntity updateBook(BookEntity bookEntity);

    BookEntity patchBook(String isbn, BookEntity bookEntity);

    void deleteByIsbn(String isbn);
}
