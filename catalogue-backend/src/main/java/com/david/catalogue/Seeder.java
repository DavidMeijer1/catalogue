package com.david.catalogue;

import com.david.catalogue.book.Book;
import com.david.catalogue.book.BookRepository;
import com.david.catalogue.review.Review;
import com.david.catalogue.review.ReviewRepository;
import com.david.catalogue.user.User;
import com.david.catalogue.user.UserRepository;
import com.david.catalogue.user.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class Seeder implements CommandLineRunner {
    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final ReviewRepository reviewRepository;

    public Seeder(BookRepository bookRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }
    public void saveUser() {
        var user = User.builder()
                .username("John")
                .password("password")
                .userRole(UserRole.USER)
                .build();
        userRepository.save(user);
    }

    @Override
    public void run(String... args) throws Exception {
        saveUser();
        var example = User.builder()
                .username("Robert")
                .password("password")
                .userRole(UserRole.USER)
                .build();
        userRepository.save(example);
        if (bookRepository.count() == 0) {
            bookRepository.saveAll(List.of(
                    new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "9780743273565", example),
                    new Book("To Kill a Mockingbird", "Harper Lee", 1960, "9780061120084", example),
                    new Book("1984", "George Orwell", 1949, "9780452284234", example),
                    new Book("Catch-22", "Joseph Heller", 1961, "0671128051", example),
                    new Book("The Catcher in the Rye", "J.D. Salinger", 1951, "9780241950432", example),
                    new Book("Brave New World", "Aldous Huxley", 1932, "9780060850524", example),
                    new Book("The Old Man and the Sea", "Ernest Hemingway", 1952, "9780684801223", example),
                    new Book("Lord of the Flies", "William Golding", 1954, "9780399501487", example),
                    new Book("The Grapes of Wrath", "John Steinbeck", 1939, "9780143039433", example),
                    new Book("The Sound and the Fury", "William Faulkner", 1929, "9780679600176", example),
                    new Book("Animal Farm", "George Orwell", 1945, "9780452284241", example),
                    new Book("A Farewell to Arms", "Ernest Hemingway", 1929, "9780099273974", example),
                    new Book("The Bell Jar", "Sylvia Plath", 1963, "9780061148514", example),
                    new Book("The Sun Also Rises", "Ernest Hemingway", 1926, "9781841594057", example),
                    new Book("The Picture of Dorian Gray", "Oscar Wilde", 1890, "9780141439570", example),
                    new Book("The Handmaid's Tale", "Margaret Atwood", 1985, "0771008139", example),
                    new Book("The Color Purple", "Alice Walker", 1982, "9781474607254", example),
                    new Book("The Road", "Cormac McCarthy", 2006, "9780307265432", example),
                    new Book("The Joy Luck Club", "Amy Tan", 1989, "9780749399573", example),
                    new Book("The Alchemist", "Paulo Coelho", 1988, "9780061122415", example)));
//            User testUser = new User("TestUser");
//            User anotherUser = new User("AnotherUser");
//            userRepository.saveAll(List.of(testUser, anotherUser));

//            var reviewOne = new Review(testUser, bookRepository.findByTitle("The Great Gatsby").get(), 9, "Very good.");
//            var reviewTwo = new Review(anotherUser, bookRepository.findByTitle("The Sound and the Fury").get(), 10, "Excellent.");
//            var reviewThree = new Review(anotherUser, bookRepository.findByTitle("The Sound and the Fury").get(), 8, "Useful.");
//            reviewRepository.saveAll(List.of(reviewOne, reviewTwo, reviewThree));
        }
    }
}
