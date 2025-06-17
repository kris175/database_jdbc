package com.dev.hari.database_jdbc.controllers;

import com.dev.hari.database_jdbc.TestDataUtil;
import com.dev.hari.database_jdbc.domain.dto.BookDto;
import com.dev.hari.database_jdbc.domain.entities.BookEntity;
import com.dev.hari.database_jdbc.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private BookService bookService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.bookService = bookService;
    }

    @Test
    public void testThatCreateBookEndpointWorks() throws Exception {
        BookDto book = TestDataUtil.getTestBookDto(null, "978-3-16-148410-0", "Effective Java");
        String bookJson = objectMapper.writeValueAsString(book);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/books/" + book.getIsbn())
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookEndpointReturnsSavedBook() throws Exception {
        BookDto book = TestDataUtil.getTestBookDto(null, "978-3-16-148410-0", "Effective Java");
        String bookJson = objectMapper.writeValueAsString(book);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/books/" + book.getIsbn())
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.isbn").value("978-3-16-148410-0")
        ).andExpect(
                MockMvcResultMatchers
                        .jsonPath("$.title").value("Effective Java")
        );
    }

    @Test
    public void testThatGetAllBooksEndpointWorks() throws Exception {
        BookEntity book1 = TestDataUtil.getTestBookEntity(null, "978-3-16-148410-0", "Effective Java 1st Edition");

        bookService.save(book1.getIsbn(), book1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/")
                        .accept(org.springframework.http.MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content.[0].isbn").value("978-3-16-148410-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content.[0].title").value("Effective Java 1st Edition")
        );
    }

    @Test
    public void testThatGetBookWithIsbnEndpointWorks() throws Exception {
        BookEntity book1 = TestDataUtil.getTestBookEntity(null, "978-3-16-148410-0", "Effective Java 1st Edition");

        bookService.save(book1.getIsbn(), book1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + book1.getIsbn())
                        .accept(org.springframework.http.MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("978-3-16-148410-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Effective Java 1st Edition")
        );
    }

    @Test
    public void testThatFullUpdateBookReturns404WhenNoBooksExists() throws Exception {
        BookDto book = TestDataUtil.getTestBookDto(null, "978-3-16-148410-0", "Effective Java");
        String bookJson = objectMapper.writeValueAsString(book);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + book.getIsbn())
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity book1 = TestDataUtil.getTestBookEntity(null, "978-3-16-148410-0", "Effective Java 1st Edition");
        bookService.save(book1.getIsbn(), book1);

        BookDto updatedBook = TestDataUtil.getTestBookDto(null, book1.getIsbn(), "Effective Java 2nd Edition");
        String bookJson = objectMapper.writeValueAsString(updatedBook);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + updatedBook.getIsbn())
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("978-3-16-148410-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Effective Java 2nd Edition")
        );
    }

    @Test
    public void testThatPartialUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity book1 = TestDataUtil.getTestBookEntity(null, "978-3-16-148410-0", "Effective Java 1st Edition");
        bookService.save(book1.getIsbn(), book1);

        BookDto updatedBook = TestDataUtil.getTestBookDto(null, book1.getIsbn(), "Effective Java 2nd Edition");
        String bookJson = objectMapper.writeValueAsString(updatedBook);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + updatedBook.getIsbn())
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("978-3-16-148410-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Effective Java 2nd Edition")
        );
    }

    @Test
    public void testThatDeleteBookByIsbnWorks() throws Exception {
        BookEntity book1 = TestDataUtil.getTestBookEntity(null, "978-3-16-148410-0", "Effective Java 1st Edition");
        bookService.save(book1.getIsbn(), book1);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/" + book1.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + book1.getIsbn())
                        .accept(org.springframework.http.MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
}
