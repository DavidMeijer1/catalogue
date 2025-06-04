import React from "react";

import { useAuth } from "../../../api/AuthContext";

function DeleteReview({ id, setReviews }) {
  const { getUser } = useAuth();
  const user = getUser();

  const HttpStatusCode = {
    NoContent: 204,
    NotFound: 404,
  };

  async function deleteReview() {
    console.log("Deleting review with id:", id); // Debugging line
    try {
      const response = await fetch(
        `http://localhost:8080/api/v1/reviews/book/${id}`,
        {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${user.accessToken}`,
          },
        }
      );
      if (response.status === HttpStatusCode.NoContent) {
        // Update the reviews in the parent component
        setReviews((prevReviews) =>
          prevReviews.filter((review) => review.id !== id)
        );
        console.log(`You have deleted the review with id ${id}.`);
        alert("You have deleted the review.");
      } else {
        console.error("You have not deleted the review:", response.status);
        const errorData = await response.json();
        console.error("See the details here", errorData);
      }
    } catch (error) {
      console.log("The computer gives this error:" + error);
    }
  }

  return (
    <div>
      <button onClick={deleteReview}>Delete the review</button>
    </div>
  );
}

export default DeleteReview;
