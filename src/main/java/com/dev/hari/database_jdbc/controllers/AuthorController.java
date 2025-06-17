package com.dev.hari.database_jdbc.controllers;

import com.dev.hari.database_jdbc.domain.dto.AuthorDto;
import com.dev.hari.database_jdbc.domain.entities.AuthorEntity;
import com.dev.hari.database_jdbc.mappers.Mapper;
import com.dev.hari.database_jdbc.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    private final Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping("/")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);
        AuthorDto savedAuthorDto = authorMapper.mapTo(savedAuthorEntity);
        return new ResponseEntity<>(savedAuthorDto, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public List<AuthorDto> getAllAuthors() {
        List<AuthorEntity> authorEntities = authorService.findAll();
        return authorEntities.stream()
                .map(authorMapper::mapTo)
                .toList();
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable Long id) {
        Optional<AuthorEntity> authorEntity = authorService.findById(id);
        return authorEntity.map(authorMapper::mapTo).orElse(null);
    }
}
