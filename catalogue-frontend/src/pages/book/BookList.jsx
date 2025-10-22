import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import Header from "../../components/header/Header";
import BookForm from "./components/BookForm";
import { emptyForms } from "../../api/Forms";
import { useAuth } from "../../api/AuthContext";

function BookList() {
  const [bookList, setBookList] = useState([]);
  const [newBook, setNewBook] = useState(emptyForms.newBook);
  const { getUser } = useAuth();
  const user = getUser();

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(`http://localhost:8080/api/v1/books`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${user.accessToken}`,
        },
      });
      const result = await response.json();
      console.log(result);
      setBookList(result);
    };

    fetchData();
  }, [setBookList, user.accessToken]);

  return (
    <>
      <Header />
      <div className="">
      <h1 className="mt-20 h-20 text-gray-100 text-5xl left-20 ">All books</h1>

      <div className="w-full flex flex-col space-y-4 items-center">
        {bookList.map((book) => (
          <Link
            key={book.id}
            to={"/books/" + book.isbnNumber}
            className="w-full flex justify-center"
          >
            <article className="w-8/12 h-20 md:mx-4 flex flex-row justify-between items-center bg-white rounded-lg shadow-lg p-4 border-gray-200 transition-transform transform duration-300 ease-in-out hover:scale-110 hover:bg-gray-100 text-gray-900">
              <div className="flex items-center md:w-48">
                <p>{book.author}</p>
              </div>
              <div className="md-flex">
                {" "}
                <p>{book.title}</p>
              </div>
              <div>
                <p>{book.publicationYear}</p>
              </div>
            </article>
          </Link>
        ))}
      </div>
      <BookForm
        bookList={bookList}
        newBook={newBook}
        setNewBook={setNewBook}
        setBookList={setBookList}
      />
      </div>
    </>
  );
}

export default BookList;
