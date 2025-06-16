package com.dev.hari.database_jdbc;

import com.dev.hari.database_jdbc.domain.Author;
import com.dev.hari.database_jdbc.domain.Book;

public final class TestDataUtil {
    private TestDataUtil() {}


    public static Author createTestAuthor(String name, int age) {
        return Author.builder()
                .name(name)
                .age(age)
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
