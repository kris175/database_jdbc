package com.dev.hari.database_jdbc.dao.impl;

import com.dev.hari.database_jdbc.TestDataUtil;
import com.dev.hari.database_jdbc.dao.AuthorDao;
import com.dev.hari.database_jdbc.domain.Author;
import com.dev.hari.database_jdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BookDaoImplIntegrationTests {

    private AuthorDao authorDao;

    private BookDaoImpl underTest;

    @Autowired
    public BookDaoImplIntegrationTests(BookDaoImpl underTest, AuthorDao authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testThatBookCanBeCreatedAndRetrieved() {

        Author author = TestDataUtil.createTestAuthor();
        authorDao.create(author);

        Book book = TestDataUtil.getTestBook();
        book.setAuthorId(author.getId());

        underTest.create(book);
        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }
}
