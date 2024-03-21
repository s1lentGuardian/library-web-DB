package org.rodionnapoleon.library.repository;

import org.junit.jupiter.api.Test;
import org.rodionnapoleon.library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.Year;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;


    @Test
    void save() {
        Book book = Book.builder()
                .author("Dummy")
                .bookName("Dummy")
                .yearOfPublishing(Year.now())
                .cost(new BigDecimal(99))
                .build();

        Book actualResult = bookRepository.save(book);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(book);
    }

    @Test
    void findById() {
        Book book = Book.builder()
                .author("Dummy")
                .bookName("Dummy")
                .yearOfPublishing(Year.now())
                .cost(new BigDecimal(99))
                .build();

        bookRepository.save(book);
        Optional<Book> actualResult = bookRepository.findById(book.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getId()).isEqualTo(book.getId());
    }

    @Test
    void deleteById() {
        Book book = Book.builder()
                .author("Dummy")
                .bookName("Dummy")
                .yearOfPublishing(Year.now())
                .cost(new BigDecimal(99))
                .build();

        bookRepository.save(book);
        bookRepository.deleteById(book.getId());
        Optional<Book> possibleBook = bookRepository.findById(book.getId());

        assertThat(possibleBook).isEmpty();
    }

    @Test
    void existsById() {
        Book book = Book.builder()
                .author("Dummy")
                .bookName("Dummy")
                .yearOfPublishing(Year.now())
                .cost(new BigDecimal(99))
                .build();

        bookRepository.save(book);
        boolean actualResult = bookRepository.existsById(book.getId());

        assertThat(actualResult).isTrue();
    }

    @Test
    void doesNotExistById() {
        Book book = Book.builder()
                .id(1L)
                .author("Dummy")
                .bookName("Dummy")
                .yearOfPublishing(Year.now())
                .cost(new BigDecimal(99))
                .build();

        boolean actualResult = bookRepository.existsById(book.getId());

        assertThat(actualResult).isFalse();

    }

}