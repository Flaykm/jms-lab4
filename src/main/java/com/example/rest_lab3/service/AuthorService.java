package com.example.rest_lab3.service;

import com.example.rest_lab3.jms.ChangeMessageSender;
import com.example.rest_lab3.model.Author;
import com.example.rest_lab3.model.Book;
import com.example.rest_lab3.repository.AuthorRepository;
import com.example.rest_lab3.repository.BookRepository;
import com.example.rest_lab3.email.EmailNotifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ChangeMessageSender changeMessageSender;
    private final EmailNotifier emailNotifier;

    public AuthorService(AuthorRepository authorRepository,
                         BookRepository bookRepository,
                         ChangeMessageSender changeMessageSender,
                         EmailNotifier emailNotifier) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.changeMessageSender = changeMessageSender;
        this.emailNotifier = emailNotifier;
    }

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> getById(Long id) {
        return authorRepository.findById(id);
    }

    public Author create(Author author) {
        Author saved = authorRepository.save(author);
        changeMessageSender.sendChange("Author", saved.getId(), "INSERT", saved.toString());
        return saved;
    }

    public Author update(Long id, Author authorDetails) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        author.setName(authorDetails.getName());
        author.setBirthYear(authorDetails.getBirthYear());

        Author updated = authorRepository.save(author);
        changeMessageSender.sendChange("Author", updated.getId(), "UPDATE", updated.toString());
        return updated;
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
        changeMessageSender.sendChange("Author", id, "DELETE", "");
    }

    // Сохранение книги (если нужно из AuthorService)
    public Book saveBook(Book book) {
        Book saved = bookRepository.save(book);
        changeMessageSender.sendChange(saved, "INSERT");

        if (saved.getYear() != null && saved.getYear() > 2027) {
            emailNotifier.sendFutureBookAlert(saved);
        }

        return saved;
    }
}
