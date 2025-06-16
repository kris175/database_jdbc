package com.dev.hari.database_jdbc.repository;

import com.dev.hari.database_jdbc.TestDataUtil;
import com.dev.hari.database_jdbc.domain.Author;
import com.dev.hari.database_jdbc.repositories.AuthorRepository;
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
public class AuthorRepositoryIntegrationTests {

    private final AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRetrieved() {
        Author author = TestDataUtil.createTestAuthor();
        underTest.save(author);
        Optional<Author> result = underTest.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

//
//    @Test
//    public void testThatMultipleAuthorsCanBeCreatedAndRetrieved() {
//        Author author1 = TestDataUtil.createTestAuthor();
//        Author author2 = TestDataUtil.createTestAuthor();
//        author2.setId(2L); // Ensure different ID for the second author
//
//        underTest.create(author1);
//        underTest.create(author2);
//
//        assertThat(underTest.findAll()).containsExactlyInAnyOrder(author1, author2);
//    }
//
//    @Test
//    public void testThatAuthorCanBeUpdated() {
//        Author author = TestDataUtil.createTestAuthor();
//        underTest.create(author);
//
//        author.setName("Updated Name");
//        author.setAge(30);
//        underTest.update(1L, author);
//
//        Optional<Author> updatedAuthor = underTest.findOne(author.getId());
//        assertThat(updatedAuthor).isPresent();
//        assertThat(updatedAuthor.get().getName()).isEqualTo("Updated Name");
//        assertThat(updatedAuthor.get().getAge()).isEqualTo(30);
//    }
//
//    @Test
//    public void testThatAuthorCanBeDeleted() {
//        Author author = TestDataUtil.createTestAuthor();
//        underTest.create(author);
//
//        underTest.delete(author.getId());
//
//        Optional<Author> deletedAuthor = underTest.findOne(author.getId());
//        assertThat(deletedAuthor).isNotPresent();
//    }
}
