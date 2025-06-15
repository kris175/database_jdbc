package com.dev.hari.database_jdbc.dao.impl.unitTests;

import com.dev.hari.database_jdbc.TestDataUtil;
import com.dev.hari.database_jdbc.dao.impl.AuthorDaoImpl;
import com.dev.hari.database_jdbc.domain.Author;
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
public class AuthorDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testThatCreateAuthorGeneratesCorrectSql() {
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);

        verify(jdbcTemplate)
                .update(
                        eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                        eq(1L),
                        eq("John"),
                        eq(20)
                );
    }

    @Test
    public void testThatFindOneGeneratesCorrectSql() {
        Long authorId = 1L;
        underTest.findOne(authorId);

        verify(jdbcTemplate)
                .query(
                        eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
                        ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
                        eq(authorId)
                );
    }

    @Test
    public void testThatFindAllGeneratesCorrectSql() {
        underTest.findAll();

        verify(jdbcTemplate)
                .query(
                        eq("SELECT id, name, age FROM authors"),
                        ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()
                );
    }

    @Test
    public void testThatUpdateAuthorGeneratesCorrectSql() {
        Author author = TestDataUtil.createTestAuthor();
        underTest.update(1L, author);

        verify(jdbcTemplate)
                .update(
                        eq("UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?"),
                        eq(1L),
                        eq("John"),
                        eq(20),
                        eq(1L)
                );
    }

    @Test
    public void testThatDeleteAuthorGeneratesCorrectSql() {
        Long authorId = 1L;
        underTest.delete(authorId);

        verify(jdbcTemplate)
                .update(
                        eq("DELETE FROM authors WHERE id = ?"),
                        eq(authorId)
                );
    }
}
