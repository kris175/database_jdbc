package com.dev.hari.database_jdbc.dao;

import com.dev.hari.database_jdbc.domain.Author;

import java.util.Optional;

public interface AuthorDao {
    void create(Author author);

    Optional<Author> findOne(Long authorId);
}
