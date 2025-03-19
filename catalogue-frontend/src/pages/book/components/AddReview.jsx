import { useState } from "react";
import { HttpStatusCode } from "axios";

import { emptyForms } from "../../../api/Forms";
import { useAuth } from "../../../api/AuthContext";

function AddReview({ setReviews, isbn }) {
  const [newReview, setNewReview] = useState(emptyForms.newReview);
  const { getUser } = useAuth();
  const user = getUser();

  function onSubmit(event) {
    event.preventDefault();
    addReview();
    console.log(event);
  }

  async function addReview() {
    if (!user || !user.data) {
      console.error("User is not defined or does not have data");
      return;
    }

    try {
      const reviewWithuser = {
        ...newReview,
        userId: user.data.id,
      };
      const reponse = await fetch(
        `http://localhost:8080/api/v1/reviews/book/${isbn}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${user.accessToken}`,
          },
          body: JSON.stringify(reviewWithuser),
        }
      );
      if (reponse.status === HttpStatusCode.Created) {
        setReviews((reviews) => [...reviews, newReview]);
        setNewReview(emptyForms.newReview);
      }
      const data = await reponse.json();
      console.log("The database has added the following:", data);
    } catch (error) {
      console.log("The database gives the following error:", error);
    }
  }

  return (
    <div>
      <form onSubmit={onSubmit}>
        <input
          type="text"
          name="Review"
          placeholder="Review"
          value={newReview.text}
          onChange={(event) =>
            setNewReview({
              ...newReview,
              text: event.target.value,
              isbnNumber: { isbn },
              user: user,
            })
          }
          required={true}
        ></input>
        <button type="submit">Add my review</button>
      </form>
    </div>
  );
}

export default AddReview;
