import { HttpStatusCode } from "axios";

import { emptyForms } from "../../../api/Forms";
import { useAuth } from "../../../api/AuthContext";

function BookForm({ newBook, bookList, setNewBook, setBookList }) {
  const { getUser } = useAuth();
  const user = getUser();

  function onSubmit(event) {
    console.log("User details:", user);

    event.preventDefault();
    const isbnNumber = newBook.isbnNumber;
    if (isbnNumber.length === 10 || isbnNumber.length === 13) {
      addBook();
    } else {
      alert(
        "The ISBN number must be either 10 digits (ISBN-10) or 13 digits (ISBN-13)."
      );
    }
    console.log(event);
  }

  async function addBook() {
    try {
      const bookWithUser = {
        ...newBook,
        userId: user.data.id,
      };
      const response = await fetch("http://localhost:8080/api/v1/books", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${user.accessToken}`, 
      },
        body: JSON.stringify(bookWithUser),
      });
      if (response.status === HttpStatusCode.Created) {
        setBookList((bookList) => [...bookList, newBook]);
        setNewBook(emptyForms.newBook);
      } else {
        alert("A book with this ISBN already exists in your list.");
      }
      const data = await response.json();
      console.log("The following has been added to the database: ", data);
    } catch (error) {
      console.log("The following error occurred:", error);
    }
  }

  return (
    <form onSubmit={onSubmit} className="h-20 ml-40">
      <input
        name="Title"
        placeholder="Title"
        value={newBook.title}
        onChange={(event) =>
          setNewBook({ ...newBook, title: event.target.value })
        }
        required={true}
      />
      <input
        type="text"
        name="Author"
        placeholder="Author"
        value={newBook.author}
        onChange={(event) =>
          setNewBook({ ...newBook, author: event.target.value })
        }
        required={true}
      />
      <input
        type="text"
        id="isbnNumber"
        name="isbnNumber"
        placeholder="ISBN Number"
        value={newBook.isbnNumber}
        onChange={(event) =>
          setNewBook({ ...newBook, isbnNumber: event.target.value })
        }
        required={true}
      />
      <button type="submit" className="border-r-rose-900 border-2">
        {" "}
        Add to the list of my books
      </button>
    </form>
  );
}

export default BookForm;
