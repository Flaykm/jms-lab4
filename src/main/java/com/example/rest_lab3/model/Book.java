package com.example.rest_lab3.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer year;

    @ManyToOne
    private Author author;

    public Book() {}

    public Book(String title, Integer year, Author author) {
        this.title = title;
        this.year = year;
        this.author = author;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }
}
