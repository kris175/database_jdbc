package com.dev.hari.database_jdbc.dao;

import com.dev.hari.database_jdbc.dao.impl.BookDaoImpl;
import com.dev.hari.database_jdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testThatCreateBookGeneratesCorrectSql() {
        Book book = Book.builder()
                .isbn("978-3-16-148410-0")
                .title("Effective Java")
                .authorId(1L)
                .build();
        underTest.create(book);

        verify(jdbcTemplate)
                .update(
                        eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                        eq("978-3-16-148410-0"),
                        eq("Effective Java"),
                        eq(1L)
                );
    }
}
