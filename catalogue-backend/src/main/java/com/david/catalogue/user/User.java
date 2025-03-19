package com.david.catalogue.user;

import com.david.catalogue.book.Book;
import com.david.catalogue.review.Review;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Jacksonized
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "users")
@Table
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;

    private String password;

    @NonNull
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @JsonBackReference
    @OneToMany
    Set<Book> books;

    @JsonBackReference
    @OneToMany
    Set<Review> reviews;

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public UserRole getUserRole() {
        return userRole;
    }


    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userRole == null) {
            System.out.println("Returns an empty list.");
            return Collections.emptyList(); // Return an empty list if userRole is null
        }
        return userRole.getAuthorities();
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }


    //    @Override
//    public String getPassword() {
//        return null;
//    }
}
