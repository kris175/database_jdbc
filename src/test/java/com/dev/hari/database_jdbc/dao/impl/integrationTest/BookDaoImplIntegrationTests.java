package com.dev.hari.database_jdbc.dao.impl.integrationTest;

import com.dev.hari.database_jdbc.TestDataUtil;
import com.dev.hari.database_jdbc.dao.AuthorDao;
import com.dev.hari.database_jdbc.dao.impl.BookDaoImpl;
import com.dev.hari.database_jdbc.domain.Author;
import com.dev.hari.database_jdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplIntegrationTests {

    private final AuthorDao authorDao;

    private final BookDaoImpl underTest;

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

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRetrieved() {
        Author author = TestDataUtil.createTestAuthor();
        authorDao.create(author);

        Book book1 = TestDataUtil.getTestBook();
        book1.setAuthorId(author.getId());
        underTest.create(book1);

        Book book2 = TestDataUtil.getTestBook();
        book2.setIsbn("978-3-16-148410-1");
        book2.setTitle("Effective Java 2nd Edition");
        book2.setAuthorId(author.getId());
        underTest.create(book2);

        assertThat(underTest.findAll()).hasSize(2);
        assertThat(underTest.findOne(book1.getIsbn())).isPresent().get().isEqualTo(book1);
        assertThat(underTest.findOne(book2.getIsbn())).isPresent().get().isEqualTo(book2);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthor();
        authorDao.create(author);

        Book book = TestDataUtil.getTestBook();
        book.setAuthorId(author.getId());
        underTest.create(book);

        Book updatedBook = new Book();
        updatedBook.setIsbn(book.getIsbn());
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthorId(author.getId());

        underTest.update(book.getIsbn(), updatedBook);

        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Updated Title");
    }
}
