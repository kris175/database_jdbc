package com.dev.hari.database_jdbc.repository;

import com.dev.hari.database_jdbc.TestDataUtil;
import com.dev.hari.database_jdbc.domain.Author;
import com.dev.hari.database_jdbc.domain.Book;
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
public class BookRepositoryIntegrationTests {

    private final AuthorRepository authorDao;

    private final BookRepository underTest;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository underTest, AuthorRepository authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testThatBookCanBeCreatedAndRetrieved() {

        Author author = TestDataUtil.createTestAuthor("John Doe", 45);
        authorDao.save(author);

        Book book = TestDataUtil.getTestBook(author, "978-3-16-148410-0", "Effective Java");

        underTest.save(book);
        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRetrieved() {
        Author author = TestDataUtil.createTestAuthor("Jane Smith", 30);
        authorDao.save(author);

        Book book1 = TestDataUtil.getTestBook(author, "978-3-16-148410-0", "Effective Java 1st Edition");
        underTest.save(book1);

        Book book2 = TestDataUtil.getTestBook(author, "978-3-16-148410-1", "Effective Java 2nd Edition");
        underTest.save(book2);

        assertThat(underTest.findAll()).hasSize(2);
        assertThat(underTest.findById(book1.getIsbn())).isPresent().get().isEqualTo(book1);
        assertThat(underTest.findById(book2.getIsbn())).isPresent().get().isEqualTo(book2);
    }

//    @Test
//    public void testThatBookCanBeUpdated() {
//        Author author = TestDataUtil.createTestAuthor();
//        authorDao.create(author);
//
//        Book book = TestDataUtil.getTestBook();
//        book.setAuthorId(author.getId());
//        underTest.create(book);
//
//        Book updatedBook = new Book();
//        updatedBook.setIsbn(book.getIsbn());
//        updatedBook.setTitle("Updated Title");
//        updatedBook.setAuthorId(author.getId());
//
//        underTest.update(book.getIsbn(), updatedBook);
//
//        Optional<Book> result = underTest.findOne(book.getIsbn());
//        assertThat(result).isPresent();
//        assertThat(result.get().getTitle()).isEqualTo("Updated Title");
//    }

//    @Test
//    public void testThatBookCanBeDeleted() {
//        Author author = TestDataUtil.createTestAuthor();
//        authorDao.create(author);
//
//        Book book = TestDataUtil.getTestBook();
//        book.setAuthorId(author.getId());
//        underTest.create(book);
//
//        underTest.delete(book.getIsbn());
//
//        Optional<Book> result = underTest.findOne(book.getIsbn());
//        assertThat(result).isNotPresent();
//    }
}
