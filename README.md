# Book Club Catalogue

A React and Spring Boot application designed for book clubs, integrating with the OpenLibrary API. This app allows users to log in, manage their profiles, and explore books.

## Features

* User Authentication: Secure login functionality for users.
* Profile Page: Users can view and edit their personal profile.
* Book Management: Ability to add and manage books in the catalogue.
* Upcoming Feature: Voting system for users to vote on books or club activities.

## Technologies Used

**Frontend:**
- React
- Tailwind CSS
- Vite
- Axios
- ESLint

**Backend:**
- Java 22
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Hibernate
- JSON Web Token (JWT)

## Installation instructions
### Prerequisites

Node.js: Required for the React frontend
Java 22
Maven: Used for building the Spring Boot application

### Clone the Repository

bash

    git clone https://github.com/yourusername/book-club-catalogue.git

### Frontend Setup

Navigate to the frontend directory:

    cd frontend   

Install dependencies:

    npm install

Start the development server:

    npm run dev

### Backend Setup

    Navigate to the backend directory:

    cd backend

Build the project:

    mvn clean install

Run the Spring Boot application:

    mvn spring-boot:run

### Database Setup

Make sure PostgreSQL is running and create a database for the application.
Update the application.properties file with your database connection information.

### Usage

After starting both the frontend and backend servers, open your browser and navigate to http://localhost:3000 to access the application.

## License

This project is licensed under the MIT License.

## Future Enhancements

Implement a voting system where users can vote for their favorite books or suggest new titles for the club.
