package com.dev.hari.database_jdbc.services;

import com.dev.hari.database_jdbc.domain.entities.AuthorEntity;

import java.util.List;

public interface AuthorService {

    public AuthorEntity createAuthor(AuthorEntity author);

    List<AuthorEntity> findAll();
}
