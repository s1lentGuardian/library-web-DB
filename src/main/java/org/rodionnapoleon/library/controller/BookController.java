package org.rodionnapoleon.library.controller;

import jakarta.persistence.EntityNotFoundException;
import org.rodionnapoleon.library.model.Book;
import org.rodionnapoleon.library.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;


    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public Iterable<Book> getAll() {
        return bookRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("/{id}")
    public Optional<Book> getById(@PathVariable Long id) {
        return bookRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Book updateById(@PathVariable Long id, @RequestBody Book updatedBook) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id " + id));

        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setBookName(updatedBook.getBookName());
        existingBook.setYearOfPublishing(updatedBook.getYearOfPublishing());
        existingBook.setCost(updatedBook.getCost());

        return bookRepository.save(existingBook);
    }

}
