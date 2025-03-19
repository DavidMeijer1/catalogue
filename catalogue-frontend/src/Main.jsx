import { useState } from "react";

import Header from "./components/header/Header";
import BookForm from "./pages/book/components/BookForm";
import BookList from "./pages/book/BookList";
import "./input.css";
import { emptyForms } from "./api/Forms";

function Main() {
  const [bookList, setBookList] = useState([]);
  const [newBook, setNewBook] = useState(emptyForms.newBook);

  return (
    <>
      <Header />
      <BookList bookList={bookList} setBookList={setBookList} />
      <BookForm
        bookList={bookList}
        newBook={newBook}
        setNewBook={setNewBook}
        setBookList={setBookList}
      />
    </>
  );
}

export default Main;
