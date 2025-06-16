package com.dev.hari.database_jdbc.repository;

import com.dev.hari.database_jdbc.TestDataUtil;
import com.dev.hari.database_jdbc.domain.entities.AuthorEntity;
import com.dev.hari.database_jdbc.domain.entities.BookEntity;
import com.dev.hari.database_jdbc.repositories.AuthorRepository;
import com.dev.hari.database_jdbc.repositories.BookRepository;
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
public class BookEntityRepositoryIntegrationTests {

    private final AuthorRepository authorDao;

    private final BookRepository underTest;

    @Autowired
    public BookEntityRepositoryIntegrationTests(BookRepository underTest, AuthorRepository authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testThatBookCanBeCreatedAndRetrieved() {

        AuthorEntity author = TestDataUtil.createTestAuthor("John Doe", 45);
        authorDao.save(author);

        BookEntity book = TestDataUtil.getTestBookEntity(author, "978-3-16-148410-0", "Effective Java");

        underTest.save(book);
        Optional<BookEntity> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRetrieved() {
        AuthorEntity author = TestDataUtil.createTestAuthor("Jane Smith", 30);
        authorDao.save(author);

        BookEntity book1 = TestDataUtil.getTestBookEntity(author, "978-3-16-148410-0", "Effective Java 1st Edition");
        underTest.save(book1);

        BookEntity book2 = TestDataUtil.getTestBookEntity(author, "978-3-16-148410-1", "Effective Java 2nd Edition");
        underTest.save(book2);

        assertThat(underTest.findAll()).hasSize(2);
        assertThat(underTest.findById(book1.getIsbn())).isPresent().get().isEqualTo(book1);
        assertThat(underTest.findById(book2.getIsbn())).isPresent().get().isEqualTo(book2);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        AuthorEntity author = TestDataUtil.createTestAuthor("John Doe", 45);
        authorDao.save(author);

        BookEntity book = TestDataUtil.getTestBookEntity(author, "978-3-16-148410-0", "Original Title");
        underTest.save(book);

        book.setTitle("Updated Title");

        underTest.save(book);

        Optional<BookEntity> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Updated Title");
    }

    @Test
    public void testThatBookCanBeDeleted() {
        AuthorEntity author = TestDataUtil.createTestAuthor("John Doe", 45);
        authorDao.save(author);

        BookEntity book = TestDataUtil.getTestBookEntity(author, "978-3-16-148410-0", "Book to be deleted");
        underTest.save(book);

        underTest.delete(book);

        Optional<BookEntity> result = underTest.findById(book.getIsbn());
        assertThat(result).isNotPresent();
    }
}
