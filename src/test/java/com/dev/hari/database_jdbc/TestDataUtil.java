package com.dev.hari.database_jdbc;

import com.dev.hari.database_jdbc.domain.Author;

public final class TestDataUtil {
    private TestDataUtil() {}


    public static Author createTestAuthor() {
        return Author.builder()
                .id(1L)
                .name("John")
                .age(20)
                .build();
    }
}
