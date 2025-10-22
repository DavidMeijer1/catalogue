package com.david.catalogue;

import com.david.catalogue.book.Book;
import com.david.catalogue.book.BookRepository;
import com.david.catalogue.review.ReviewRepository;
import com.david.catalogue.user.User;
import com.david.catalogue.user.UserRepository;
import com.david.catalogue.user.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

    @Override
    public void run(String... args) throws Exception {
        Optional<User> existingUser = userRepository.findByUsername("Robert");
        User user;
        if(existingUser.isEmpty()){
            user = User.builder().username("Robert").password("password").userRole(UserRole.USER).build();
            userRepository.save(user);
        } else {
            user = existingUser.get();
        }
        if (bookRepository.count() == 0) {
            bookRepository.saveAll(List.of(
                    new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "9780743273565", user),
                    new Book("To Kill a Mockingbird", "Harper Lee", 1960, "9780061120084", user),
                    new Book("1984", "George Orwell", 1949, "9780452284234", user),
                    new Book("Catch-22", "Joseph Heller", 1961, "0671128051", user),
                    new Book("The Catcher in the Rye", "J.D. Salinger", 1951, "9780241950432", user),
                    new Book("Brave New World", "Aldous Huxley", 1932, "9780060850524", user),
                    new Book("The Old Man and the Sea", "Ernest Hemingway", 1952, "9780684801223", user),
                    new Book("Lord of the Flies", "William Golding", 1954, "9780399501487", user),
                    new Book("The Grapes of Wrath", "John Steinbeck", 1939, "9780143039433", user),
                    new Book("The Sound and the Fury", "William Faulkner", 1929, "9780679600176", user),
                    new Book("Animal Farm", "George Orwell", 1945, "9780452284241", user),
                    new Book("A Farewell to Arms", "Ernest Hemingway", 1929, "9780099273974", user),
                    new Book("The Bell Jar", "Sylvia Plath", 1963, "9780061148514", user),
                    new Book("The Sun Also Rises", "Ernest Hemingway", 1926, "9781841594057", user),
                    new Book("The Picture of Dorian Gray", "Oscar Wilde", 1890, "9780141439570", user),
                    new Book("The Handmaid's Tale", "Margaret Atwood", 1985, "0771008139", user),
                    new Book("The Color Purple", "Alice Walker", 1982, "9781474607254", user),
                    new Book("The Road", "Cormac McCarthy", 2006, "9780307265432", user),
                    new Book("The Joy Luck Club", "Amy Tan", 1989, "9780749399573", user),
                    new Book("The Alchemist", "Paulo Coelho", 1988, "9780061122415", user)));
        }
    }
}
