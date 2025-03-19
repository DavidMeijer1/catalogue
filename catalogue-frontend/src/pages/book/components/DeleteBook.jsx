import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function DeleteBook({isbn}) {
  const [bookList, setBookList] = useState([]);
  const navigate = useNavigate();

  const HttpStatusCode = {
    NoContent: 204,
    NotFound: 404,
  };

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(`http://localhost:8080/api/v1/books`);
      const result = await response.json();
      setBookList(result);
    };

    fetchData();
  }, [setBookList]);

  async function deleteBook(isbn) {
    try {
      setBookList((bookList) =>
        bookList.filter((book) => book.isbnNumber !== isbn)
      );

      const response = await fetch(
        `http://localhost:8080/api/v1/books/${isbn}`,
        {
          method: "DELETE",
        }
      );
      if (response.status === HttpStatusCode.NoContent) {
        console.log(
          `You have deleted the book with ISBN number ${isbn}.`
        );
        alert(`You have deleted the book.`);
        navigate(-1);
      } else {
        console.error(
          "You have not deleted the book. You can see the following error",
          response.status
        );
        const errorData = await response.json();
        console.error("See the details here:", errorData);
        setBookList((bookList) => [...bookList, { isbn }]);
      }
    } catch (error) {
      console.log("The computer gives this error:", error);
    }
  }

  return (
    <>
      <button onClick={() => deleteBook(isbn)}>Remove the book from your list</button>
    </>
  );
}

export default DeleteBook;
