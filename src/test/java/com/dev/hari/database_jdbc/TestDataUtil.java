package com.dev.hari.database_jdbc;

import com.dev.hari.database_jdbc.domain.Author;
import com.dev.hari.database_jdbc.domain.Book;

public final class TestDataUtil {
    private TestDataUtil() {}


    public static Author createTestAuthor() {
        return Author.builder()
                .id(1L)
                .name("John")
                .age(20)
                .build();
    }

    public static Book getTestBook() {
        Book book = Book.builder()
                .isbn("978-3-16-148410-0")
                .title("Effective Java")
                .authorId(1L)
                .build();
        return book;
    }
}
