package com.dev.hari.database_jdbc.dao.impl;

import com.dev.hari.database_jdbc.TestDataUtil;
import com.dev.hari.database_jdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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
        Book book = TestDataUtil.getTestBook();
        underTest.create(book);

        verify(jdbcTemplate)
                .update(
                        eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                        eq("978-3-16-148410-0"),
                        eq("Effective Java"),
                        eq(1L)
                );
    }

    @Test
    public void testThatFindOneGeneratesCorrectSql() {
        String isbn = "978-3-16-148410-0";
        underTest.findOne(isbn);

        verify(jdbcTemplate)
                .query(
                        eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
                        ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                        eq(isbn)
                );
    }
}
