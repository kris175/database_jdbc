package com.dev.hari.database_jdbc.controllers;

import com.dev.hari.database_jdbc.domain.dto.BookDto;
import com.dev.hari.database_jdbc.domain.entities.BookEntity;
import com.dev.hari.database_jdbc.mappers.Mapper;
import com.dev.hari.database_jdbc.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    private final Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping("/{isbn}")
    public ResponseEntity<BookDto> createBook(
            @RequestBody BookDto book,
            @PathVariable String isbn) {
        BookEntity bookEntity = bookMapper.mapFrom(book);
        BookEntity savedBookEntity = bookService.createBook(isbn, bookEntity);
        BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);
        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public List<BookDto> getAllBooks() {
        List<BookEntity> bookEntities = bookService.findAll();
        return bookEntities.stream()
                .map(bookMapper::mapTo)
                .toList();
    }
}
