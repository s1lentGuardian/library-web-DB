package org.rodionnapoleon.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rodionnapoleon.library.model.Book;
import org.rodionnapoleon.library.repository.BookRepository;
import org.rodionnapoleon.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.Year;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getAll() throws Exception {
        Book book = Book.builder()
                .id(1L)
                .author("dummy")
                .bookName("dummy")
                .yearOfPublishing(Year.now())
                .cost(new BigDecimal(99))
                .build();
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        ResultActions response  = mockMvc.perform(get("/api/book")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void create() throws Exception {
        Book book = Book.builder()
                .id(1L)
                .author("dummy")
                .bookName("dummy")
                .yearOfPublishing(Year.now())
                .cost(new BigDecimal(99))
                .build();
        when(bookService.save(any(Book.class))).thenReturn(book);

        ResultActions response = mockMvc.perform(post("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookName", CoreMatchers.is(book.getBookName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", CoreMatchers.is(book.getAuthor())))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void getById() throws Exception {
        Book book = Book.builder()
                .id(1L)
                .author("dummy")
                .bookName("dummy")
                .yearOfPublishing(Year.now())
                .cost(new BigDecimal(99))
                .build();
        when(bookService.findById(1L)).thenReturn(Optional.of(book));

        ResultActions response = mockMvc.perform(get("/api/book/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", CoreMatchers.is(book.getAuthor())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookName", CoreMatchers.is(book.getBookName())));
    }

    @Test
    void deleteById() throws Exception {
        doNothing().when(bookService).deleteById(1L);

        ResultActions response = mockMvc.perform(delete("/api/book/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void updateById() throws Exception {
        Book book = Book.builder()
                .id(1L)
                .author("dummy")
                .bookName("dummy")
                .yearOfPublishing(Year.now())
                .cost(new BigDecimal(99))
                .build();
        when(bookService.updateById(1L, book)).thenReturn(book);

        ResultActions response = mockMvc.perform(put("/api/book/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookName", CoreMatchers.is(book.getBookName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", CoreMatchers.is(book.getAuthor())));
    }
}