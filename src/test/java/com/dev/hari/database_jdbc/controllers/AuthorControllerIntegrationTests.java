package com.dev.hari.database_jdbc.controllers;

import com.dev.hari.database_jdbc.TestDataUtil;
import com.dev.hari.database_jdbc.domain.entities.AuthorEntity;
import com.dev.hari.database_jdbc.services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

    private AuthorService authorService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.authorService = authorService;
    }

    @Test
    public void testThatCreateAuthorEndpointWorks() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor("John Doe", 45);
        testAuthor.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorEndpointReturnsSavedAuthor() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor("John Doe", 45);
        testAuthor.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.name").value("John Doe")
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.age").value(45)
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatGetAllAuthorsEndpointWorks() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAllAuthorsEndpointReturnsListOfAuthors() throws Exception {
        AuthorEntity testAuthor1 = TestDataUtil.createTestAuthor("John Doe", 35);

        authorService.save(testAuthor1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].name").value("John Doe")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].age").value(35)
        );
    }

    @Test
    public void testThatGetAuthorWithIdEndpointWorks() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor("Jane Doe", 30);
        AuthorEntity savedAuthor = authorService.save(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + savedAuthor.getId())
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(savedAuthor.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(savedAuthor.getAge())
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturns404WhenNoAuthorsExists() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor("Jane Doe", 30);
        authorService.save(testAuthor);
        testAuthor.setId(999L); // Assuming this ID does not exist
        String authorJson = objectMapper.writeValueAsString(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + testAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturnsUpdatedAuthor() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor("Jane Doe", 30);
        AuthorEntity savedAuthor = authorService.save(testAuthor);
        savedAuthor.setName("Jane Smith");
        savedAuthor.setAge(35);
        String authorJson = objectMapper.writeValueAsString(savedAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Jane Smith")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(35)
        );
    }
}
