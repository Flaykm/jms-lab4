package com.example.rest_lab3.service;

import com.example.rest_lab3.email.EmailNotifier;
import com.example.rest_lab3.model.Book;
import com.example.rest_lab3.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EmailNotifier emailNotifier;

    public Book create(Book book) {
        Book saved = bookRepository.save(book);
        emailNotifier.sendChange("Book", saved.getId(), "INSERT", saved);
        return saved;
    }

    public Book update(Book book) {
        Book updated = bookRepository.save(book);
        emailNotifier.sendChange("Book", updated.getId(), "UPDATE", updated);
        return updated;
    }

    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            bookRepository.deleteById(id);
            emailNotifier.sendChange("Book", id, "DELETE", book);
        }
    }

    public Book getById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }
}
