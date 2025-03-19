package com.david.catalogue.book;

import com.david.catalogue.review.Review;
import com.david.catalogue.user.User;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.extern.jackson.Jacksonized;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Book {

    @JsonManagedReference
    @OneToMany(mappedBy = "book")
    private final Set<Review> reviews = new HashSet<>();
    private String title;
    private String author;
    private int publicationYear;
    private String isbnNumber;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Id
    @GeneratedValue
    private Long id;

    public Book(String title, String author, int publicationYear, String isbnNumber, User user) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbnNumber = isbnNumber;
        this.user = user;
    }

    Book() {
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    public Long getId() {
        return id;
    }

    public Set<Review> getReviews() {
        return reviews;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
