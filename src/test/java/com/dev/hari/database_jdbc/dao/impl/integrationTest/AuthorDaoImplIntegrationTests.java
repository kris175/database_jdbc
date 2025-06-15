package com.dev.hari.database_jdbc.dao.impl.integrationTest;

import com.dev.hari.database_jdbc.TestDataUtil;
import com.dev.hari.database_jdbc.dao.impl.AuthorDaoImpl;
import com.dev.hari.database_jdbc.domain.Author;
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
public class AuthorDaoImplIntegrationTests {

    private final AuthorDaoImpl underTest;

    @Autowired
    public AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRetrieved() {
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRetrieved() {
        Author author1 = TestDataUtil.createTestAuthor();
        Author author2 = TestDataUtil.createTestAuthor();
        author2.setId(2L); // Ensure different ID for the second author

        underTest.create(author1);
        underTest.create(author2);

        assertThat(underTest.findAll()).containsExactlyInAnyOrder(author1, author2);
    }
}
