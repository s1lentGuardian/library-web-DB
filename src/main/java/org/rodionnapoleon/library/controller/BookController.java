package org.rodionnapoleon.library.controller;

import org.rodionnapoleon.library.model.Book;
import org.rodionnapoleon.library.repository.BookRepository;
import org.rodionnapoleon.library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookRepository bookRepository;
    private final BookService bookService;


    public BookController(BookRepository bookRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    @GetMapping
    public Iterable<Book> getAll() {
        return bookRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Book create(@RequestBody Book book)  {
        return bookService.save(book);
    }

    @GetMapping("/{id}")
    public Optional<Book> getById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }


    @PutMapping("/{id}")
    public Book updateById(@PathVariable Long id, @RequestBody Book updatedBook) {
        return bookService.updateById(id, updatedBook);
    }

}
