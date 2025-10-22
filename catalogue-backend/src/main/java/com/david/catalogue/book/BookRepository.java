package com.david.catalogue.book;

import com.david.catalogue.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
    Optional<Book> findByIsbnNumber(String isbnNumber);
    void deleteByIsbnNumber(String isbnNumber);
    Iterable<Book> findByUser(User user);
}
