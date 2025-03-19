import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";

import Header from "../../components/header/Header";
import ReviewList from "./components/ReviewList";
import DeleteBook from "./components/DeleteBook";

function Book() {
  const [book, setBook] = useState([]);
  const [bookFromDatabase, setBookFromDatabase] = useState([]);
  const { isbn } = useParams();

  const HttpStatusCode = {
    NoContent: 204,
    NotFound: 404,
  };

  useEffect(() => {
    const fetchBook = async () => {
      const reponse = await fetch(
        `https://openlibrary.org/search.json?q=${isbn}`
      );
      const data = await reponse.json();

      setBook(data.docs[0]);
    };
    fetchBook();
  }, [isbn]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(
        `http://localhost:8080/api/v1/books/${isbn}`,
      );
      const result = await response.json();
      console.log(result);
      setBookFromDatabase(result);
    };

    fetchData();
  }, [isbn]);

  return (
    <div className="">
      <Header />

      <div className="flex flex-col md:flex-row items-center justify-center bg-slate-50 p-8">
        <div className="">
          <h1 className="text-3xl font-bold mb-2 text-black">{book.title}</h1>
          <p className="text-gray-500 mb-4">
            by{" "}
            {Array.isArray(book.author_name)
              ? book.author_name[0]
              : book.author_name}
          </p>
          <p className="text-gray-700 mb-4">
            First published in {book.first_publish_year}
          </p>
          <p className="text-gray-700 mb-4">
            Number of pages: {book.number_of_pages_median}
          </p>
          <div />

          <div className="text-gray-700 mb-4">
            First sentence: {book.first_sentence}
          </div>

          <div className="text-gray-700 mb-4">
            International Standard Book Number (ISBN): {isbn}
          </div>

          <div className="bg-white p-6 rounded-lg shadow-lg">
            Cover:
            <img
              src={`https://covers.openlibrary.org/b/id/${book.cover_i}-M.jpg`}
              className="w-64 h-96 object-cover rounded-lg shadow-lg"
            />
          </div>

          <div>{bookFromDatabase.id}</div>
        </div>
      </div>
      {/* <button onClick={() => deleteBook(isbn)} >Remove the book from your list</button> */}

      <div>
        <ReviewList isbn={isbn} id={bookFromDatabase.id}/>
        <DeleteBook isbn={isbn} />
      </div>
    </div>
  );
}

export default Book;
