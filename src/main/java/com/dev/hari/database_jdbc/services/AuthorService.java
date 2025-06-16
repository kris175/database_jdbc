package com.dev.hari.database_jdbc.services;

import com.dev.hari.database_jdbc.domain.entities.AuthorEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    public AuthorEntity createAuthor(AuthorEntity author);
}
