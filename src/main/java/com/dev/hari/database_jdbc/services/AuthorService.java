package com.dev.hari.database_jdbc.services;

import com.dev.hari.database_jdbc.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    public AuthorEntity save(AuthorEntity author);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findById(Long id);

    boolean doesExists(Long id);
}
