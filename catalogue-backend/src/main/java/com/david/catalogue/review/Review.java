package com.david.catalogue.review;

import com.david.catalogue.book.Book;
import com.david.catalogue.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JsonManagedReference
    private User user;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private int rating;

    private String text;

    private LocalDateTime datePosted;

    Review() {
    }

    public Review(User user, Book book, int rating, String text, LocalDateTime datePosted) {
        this.user = user;
        this.book = book;
        this.rating = rating;
        this.text = text;
        this.datePosted = datePosted;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public int getRating() {
        return rating;
    }

    public String getText() {
        return text;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public Long getId() {
        return id;
    }
}
