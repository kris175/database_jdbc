package com.dev.hari.database_jdbc.services.impl;

import com.dev.hari.database_jdbc.domain.entities.AuthorEntity;
import com.dev.hari.database_jdbc.domain.entities.BookEntity;
import com.dev.hari.database_jdbc.repositories.AuthorRepository;
import com.dev.hari.database_jdbc.repositories.BookRepository;
import com.dev.hari.database_jdbc.services.AuthorService;
import com.dev.hari.database_jdbc.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public Optional<BookEntity> findByIsbn(String isbn) {
        return bookRepository.findById(isbn);
    }
}
