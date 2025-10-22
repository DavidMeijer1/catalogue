package com.david.catalogue.book;

import com.david.catalogue.review.ReviewRepository;
import com.david.catalogue.user.User;
import com.david.catalogue.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public BookController(BookRepository bookRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping
    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn){
        return bookRepository.findByIsbnNumber(isbn).get();
    }

    @GetMapping("/ownbooks")
    public Iterable<Book> getMybooks(@AuthenticationPrincipal User currentUser){
        Iterable books = bookRepository.findByUser(currentUser);
        return bookRepository.findByUser(currentUser);
    }

    @GetMapping("/search/titles/{title}")
    public ResponseEntity<Book> findByTitle(@PathVariable String title) {
        Optional<Book> possibleBook = bookRepository.findByTitle(title);
        return possibleBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> addBook(@Validated @RequestBody BookDTO bookDTO, @AuthenticationPrincipal User currentUser, UriComponentsBuilder ucb) {
        try {
            if(bookRepository.findByIsbnNumber(bookDTO.isbnNumber).isPresent()){
                return ResponseEntity.badRequest().build();
            }
            var book = new Book();
            book.setAuthor(bookDTO.author);
            book.setTitle(bookDTO.title);
            book.setIsbnNumber(bookDTO.isbnNumber);
            book.setUser(currentUser);
            bookRepository.save(book);
            URI locationOfBook = ucb
                    .path("/books/{isbn}")
                    .buildAndExpand(book.getIsbnNumber())
                    .toUri();
            return ResponseEntity.created(locationOfBook).body(book);
        } catch(RuntimeException runtimeException){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Void> replace(@PathVariable Book book) {
        var id = book.getId();
        if (id == null) return ResponseEntity.badRequest().build();
        var possibleOriginalMovie = bookRepository.findById(id);
        if (possibleOriginalMovie.isEmpty()) return ResponseEntity.notFound().build();
        bookRepository.save(book);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("/{isbnNumber}")
    public ResponseEntity<Void> delete(@PathVariable String isbnNumber) {
        if (bookRepository.findByIsbnNumber(isbnNumber).isPresent()) {
            reviewRepository.deleteAllByBookIsbnNumber(isbnNumber);
            bookRepository.deleteByIsbnNumber(isbnNumber);
            return ResponseEntity.noContent().build();
        } else return ResponseEntity.notFound().build();
    }

    public record BookDTO(String author, String title, String isbnNumber){}

}
