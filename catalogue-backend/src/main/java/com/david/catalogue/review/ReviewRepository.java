package com.david.catalogue.review;

import com.david.catalogue.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Iterable<Review> findByBookId(long id);
    Iterable<Review> findByBookIsbnNumber(String isbnNumber);
    Iterable<Review> findByUser(User user);
    }

