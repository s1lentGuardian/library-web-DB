package org.rodionnapoleon.library.service;

import org.rodionnapoleon.library.exception.BookNotFoundException;
import org.rodionnapoleon.library.model.Book;
import org.rodionnapoleon.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> findById(Long id) {
        if (bookRepository.findById(id).isEmpty())
            throw new BookNotFoundException(STR."Book not found with id: \{id}");
        return bookRepository.findById(id);
    }

    public void deleteById(Long id) {
        if (!bookRepository.existsById(id))
            throw new BookNotFoundException("Book is not found");
        bookRepository.deleteById(id);
    }

    public Book updateById(Long id, Book updatedBook) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book is not found"));

        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setBookName(updatedBook.getBookName());
        existingBook.setYearOfPublishing(updatedBook.getYearOfPublishing());
        existingBook.setCost(updatedBook.getCost());
        return bookRepository.save(existingBook);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }
}
