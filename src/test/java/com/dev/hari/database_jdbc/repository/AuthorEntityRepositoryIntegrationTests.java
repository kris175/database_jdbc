package com.dev.hari.database_jdbc.repository;

import com.dev.hari.database_jdbc.TestDataUtil;
import com.dev.hari.database_jdbc.domain.entities.AuthorEntity;
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
public class AuthorEntityRepositoryIntegrationTests {

    private final AuthorRepository underTest;

    @Autowired
    public AuthorEntityRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRetrieved() {
        AuthorEntity author = TestDataUtil.createTestAuthor("John Doe", 45);
        underTest.save(author);
        Optional<AuthorEntity> result = underTest.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }


    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRetrieved() {
        AuthorEntity author1 = TestDataUtil.createTestAuthor("Author One", 25);
        AuthorEntity author2 = TestDataUtil.createTestAuthor("Author Two", 60);// Ensure different ID for the second author

        underTest.save(author1);
        underTest.save(author2);

        assertThat(underTest.findAll()).containsExactlyInAnyOrder(author1, author2);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        AuthorEntity author = TestDataUtil.createTestAuthor("Jane Smith", 30);
        underTest.save(author);

        author.setName("John Doe");
        author.setAge(40);
        underTest.save(author);

        Optional<AuthorEntity> updatedAuthor = underTest.findById(author.getId());
        assertThat(updatedAuthor).isPresent();
        assertThat(updatedAuthor.get().getName()).isEqualTo(author.getName());
        assertThat(updatedAuthor.get().getAge()).isEqualTo(author.getAge());
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        AuthorEntity author = TestDataUtil.createTestAuthor("Mark Twain", 60);
        underTest.save(author);

        underTest.delete(author);

        Optional<AuthorEntity> deletedAuthor = underTest.findById(author.getId());
        assertThat(deletedAuthor).isNotPresent();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan() {
        AuthorEntity author1 = TestDataUtil.createTestAuthor("Alice", 25);
        AuthorEntity author2 = TestDataUtil.createTestAuthor("Bob", 35);
        AuthorEntity author3 = TestDataUtil.createTestAuthor("Charlie", 45);

        underTest.save(author1);
        underTest.save(author2);
        underTest.save(author3);

        Iterable<AuthorEntity> results = underTest.findAuthorByAgeLessThan(36);

        assertThat(results).containsExactlyInAnyOrder(author1, author2);
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan() {
        AuthorEntity author1 = TestDataUtil.createTestAuthor("Alice", 25);
        AuthorEntity author2 = TestDataUtil.createTestAuthor("Bob", 35);
        AuthorEntity author3 = TestDataUtil.createTestAuthor("Charlie", 45);

        underTest.save(author1);
        underTest.save(author2);
        underTest.save(author3);

        Iterable<AuthorEntity> results = underTest.findAuthorsWithAgeGreaterThan(36);
        assertThat(results).containsExactly(author3);
    }
}
