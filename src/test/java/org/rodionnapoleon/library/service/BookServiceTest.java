package org.rodionnapoleon.library.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rodionnapoleon.library.exception.BookNotFoundException;
import org.rodionnapoleon.library.model.Book;
import org.rodionnapoleon.library.repository.BookRepository;

import java.math.BigDecimal;
import java.time.Year;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;

    @Test
    void save() {
        Book book = getBook();
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book actualResult = bookService.save(book);

        assertNotNull(actualResult);
        assertThat(actualResult).isEqualTo(book);
        verify(bookRepository).save(book);
    }

    @Test
    void getExistingBookById() {
        Book book = getBook();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> actualResult = bookService.findById(1L);

        assertThat(actualResult).isPresent();
        assertThat(actualResult).isNotNull();
    }

    @Test
    void getNotExistingBookById() {
        when(bookRepository.findById(1L)).thenThrow(BookNotFoundException.class);

        assertThrows(BookNotFoundException.class, () -> bookService.findById(1L));
    }

    @Test
    void deleteExistingBookById() {
        Book book = getBook();
        book.setId(1L);
        when(bookRepository.existsById(book.getId())).thenReturn(true);

        bookService.deleteById(1L);

        verify(bookRepository).deleteById(1L);
    }

    @Test
    void deleteNotExistingBookById() {
        when(bookRepository.existsById(1L)).thenThrow(BookNotFoundException.class);

        assertThrows(BookNotFoundException.class, () -> bookService.deleteById(1L));
    }

    @Test
    void updateBookByExistingId() {
        Book book = getBook();
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Book updatedBook = bookService.updateById(1L, book);

        assertThat(updatedBook).isNotNull();
        verify(bookRepository).save(updatedBook);

    }

    @Test
    void updateBookByNotExistingId() {
        Book book = getBook();
        book.setId(1L);
        when(bookRepository.findById(1L)).thenThrow(BookNotFoundException.class);

        assertThrows(BookNotFoundException.class, () -> bookService.updateById(1L, book));
    }

    private Book getBook() {
        Book book = Book.builder()
                .author("Frank Gerbert")
                .bookName("Dune")
                .yearOfPublishing(Year.of(1965))
                .cost(new BigDecimal(99))
                .build();
        return book;
    }
}