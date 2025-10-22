package com.david.catalogue.review;

import com.david.catalogue.book.Book;
import com.david.catalogue.book.BookRepository;
import com.david.catalogue.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/reviews/book")
public class ReviewController {
    private final ReviewRepository reviewRepository;

    private final BookRepository bookRepository;

    public ReviewController(ReviewRepository reviewRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping
    Iterable<Review> getAllReviews(){
        return reviewRepository.findAll();
    }
    @GetMapping("/{isbn}")
    Iterable<Review> getReviewsByBookIsbnNumber(@PathVariable String isbn){
        return reviewRepository.findByBookIsbnNumber(isbn);
    }

    @GetMapping("/ownreviews")
    public Iterable<Review> getMyreviews(@AuthenticationPrincipal User currentUser){
        return reviewRepository.findByUser(currentUser);
    }

    @PostMapping("/{isbn}")
    public ResponseEntity<?> addReview(@PathVariable String isbn, @AuthenticationPrincipal User currentUser, @RequestBody ReviewDTO reviewDTO, UriComponentsBuilder ucb){
        try {
            Book book = bookRepository.findByIsbnNumber(isbn).get();

            var review = new Review();
            review.setText(reviewDTO.text);
            review.setDatePosted(LocalDateTime.now());
            review.setBook(book);
            review.setUser(currentUser);
            reviewRepository.save(review);

            URI locationOfReview = ucb.
                    path("api/v1/books/reviews/{isbnNumber}").build().toUri();
            return ResponseEntity.created(locationOfReview).body(review);
        }
        catch (RuntimeException runtimeException){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@AuthenticationPrincipal User currentUser, @PathVariable Long id){
        if(reviewRepository.existsById(id)){
            reviewRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    public record ReviewDTO(String text, LocalDateTime dateTimePosted){ }

}
