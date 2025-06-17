package com.dev.hari.database_jdbc.services.impl;

import com.dev.hari.database_jdbc.domain.entities.AuthorEntity;
import com.dev.hari.database_jdbc.domain.entities.BookEntity;
import com.dev.hari.database_jdbc.repositories.AuthorRepository;
import com.dev.hari.database_jdbc.repositories.BookRepository;
import com.dev.hari.database_jdbc.services.AuthorService;
import com.dev.hari.database_jdbc.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public BookEntity save(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Optional<BookEntity> findByIsbn(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public boolean doesExists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public BookEntity updateBook(BookEntity bookEntity) {
        return bookRepository.save(bookEntity);
    }

    @Override
    public BookEntity patchBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.findById(isbn).map(existingBook -> {
            Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
            Optional.ofNullable(bookEntity.getAuthor()).ifPresent(existingBook::setAuthor);
            return bookRepository.save(existingBook);
        }).orElseThrow(() -> new RuntimeException("Book with ISBN " + isbn + " does not exist"));
    }

    @Override
    public void deleteByIsbn(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
