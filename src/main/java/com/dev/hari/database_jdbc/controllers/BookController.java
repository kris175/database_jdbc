package com.dev.hari.database_jdbc.controllers;

import com.dev.hari.database_jdbc.domain.dto.BookDto;
import com.dev.hari.database_jdbc.domain.entities.BookEntity;
import com.dev.hari.database_jdbc.mappers.Mapper;
import com.dev.hari.database_jdbc.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    private final Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping("/")
    public List<BookDto> getAllBooks() {
        List<BookEntity> bookEntities = bookService.findAll();
        return bookEntities.stream()
                .map(bookMapper::mapTo)
                .toList();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDto> getBookByIsbn(@PathVariable String isbn) {
        Optional<BookEntity> foundBookEntity = bookService.findByIsbn(isbn);
        return foundBookEntity.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{isbn}")
    public ResponseEntity<BookDto> createBook(
            @RequestBody BookDto book,
            @PathVariable String isbn) {
        BookEntity bookEntity = bookMapper.mapFrom(book);
        BookEntity savedBookEntity = bookService.save(isbn, bookEntity);
        BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);
        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDto> updateBook(
            @PathVariable String isbn,
            @RequestBody BookDto book) {
        if (!bookService.doesExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookEntity bookEntity = bookMapper.mapFrom(book);
        BookEntity updatedBookEntity = bookService.updateBook(bookEntity);
        BookDto updatedBookDto = bookMapper.mapTo(updatedBookEntity);
        return new ResponseEntity<>(updatedBookDto, HttpStatus.OK);
    }

    @PatchMapping("/{isbn}")
    public ResponseEntity<BookDto> patchBook(
            @PathVariable String isbn,
            @RequestBody BookDto book) {
        if (!bookService.doesExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookEntity bookEntity = bookMapper.mapFrom(book);
        BookEntity patchedBookEntity = bookService.patchBook(isbn, bookEntity);
        BookDto patchedBookDto = bookMapper.mapTo(patchedBookEntity);
        return new ResponseEntity<>(patchedBookDto, HttpStatus.OK);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable String isbn) {
        if (!bookService.doesExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.deleteByIsbn(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
