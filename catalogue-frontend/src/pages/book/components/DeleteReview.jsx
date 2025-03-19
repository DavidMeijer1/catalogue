import React from "react";

function DeleteReview({ reviewId, isbn, setReviews }) {
  const HttpStatusCode = {
    NoContent: 204,
    NotFound: 404,
  };

  async function deleteReview() {
    console.log("Deleting review with ID:", reviewId); // Debugging line
    try {
      const response = await fetch(
        `http://localhost:8080/api/v1/reviews/book/${reviewId}`,
        {
          method: "DELETE",
        }
      );
      if (response.status === HttpStatusCode.NoContent) {
        // Update the reviews in the parent component
        setReviews((prevReviews) =>
          prevReviews.filter((review) => review.id !== reviewId)
        );
        console.log(`You have deleted the review with ID ${reviewId}.`);
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
