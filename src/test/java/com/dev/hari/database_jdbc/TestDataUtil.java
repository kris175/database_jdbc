package com.dev.hari.database_jdbc;

import com.dev.hari.database_jdbc.domain.dto.AuthorDto;
import com.dev.hari.database_jdbc.domain.dto.BookDto;
import com.dev.hari.database_jdbc.domain.entities.AuthorEntity;
import com.dev.hari.database_jdbc.domain.entities.BookEntity;

public final class TestDataUtil {
    private TestDataUtil() {}


    public static AuthorEntity createTestAuthor(String name, int age) {
        return AuthorEntity.builder()
                .name(name)
                .age(age)
                .build();
    }

    public static BookEntity getTestBookEntity(AuthorEntity author, String isbn, String title) {
        return BookEntity.builder()
                .isbn(isbn)
                .title(title)
                .author(author)
                .build();
    }

    public static BookDto getTestBookDto(AuthorDto author, String isbn, String title) {
        return BookDto.builder()
                .isbn(isbn)
                .title(title)
                .author(author)
                .build();
    }
}
