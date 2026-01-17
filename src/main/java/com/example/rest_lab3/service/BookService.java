package com.example.rest_lab3.service;

import com.example.rest_lab3.model.Book;
import com.example.rest_lab3.repository.BookRepository;
import com.example.rest_lab3.jms.ChangeEventProducer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ChangeEventProducer changeEventProducer;

    public BookService(BookRepository bookRepository, ChangeEventProducer changeEventProducer) {
        this.bookRepository = bookRepository;
        this.changeEventProducer = changeEventProducer;
    }

    public Book create(Book book) {
        Book saved = bookRepository.save(book);
        changeEventProducer.sendChange(saved, "INSERT");
        return saved;
    }

    public Book update(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitle(bookDetails.getTitle());
        book.setYear(bookDetails.getYear());
        book.setAuthor(bookDetails.getAuthor());
        Book updated = bookRepository.save(book);
        changeEventProducer.sendChange(updated, "UPDATE");
        return updated;
    }

    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            bookRepository.deleteById(id);
            changeEventProducer.sendChange(book, "DELETE");
        }
    }

    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }
}
