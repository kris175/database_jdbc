package com.dev.hari.database_jdbc;

import com.dev.hari.database_jdbc.domain.Author;
import com.dev.hari.database_jdbc.domain.Book;

public final class TestDataUtil {
    private TestDataUtil() {}


    public static Author createTestAuthor() {
        return Author.builder()
                .name("John")
                .age(20)
                .build();
    }

    public static Book getTestBook(Author author, String isbn, String title) {
        return Book.builder()
                .isbn(isbn)
                .title(title)
                .author(author)
                .build();
    }
}
