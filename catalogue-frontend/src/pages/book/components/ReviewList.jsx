import { useState, useEffect } from "react";

import AddReview from "./AddReview";
import DeleteReview from "./DeleteReview";
import { useAuth } from "../../../api/AuthContext";

function ReviewList({ isbn }) {
  const [reviews, setReviews] = useState([]);
  const { getUser } = useAuth();
  const user = getUser();

  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const reponse = await fetch(
          `http://localhost:8080/api/v1/reviews/book/${isbn}`,
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${user.accessToken}`,
            },
          }
        );
        const data = await reponse.json();
        const reviewsArray = Object.values(data)[0];
        setReviews(data);
        console.log(data);
      } catch (error) {
        console.log("The following error occurred:", error);
      }
    };
    fetchReviews();
  }, [isbn, user.accessToken]);

  return (
    <div>
      {reviews.length === 0 ? (
        <p>No reviews available.</p>
      ) : (
        reviews.map((review) => (
          <div key={review.id} className="">
            <article>
              {console.log(review, review.id)}
              <p>User: {review.user.username}</p>
              <p>Text: {review.text}</p>
              <p>Date: {review.dateTimePosted}</p>
            </article>
            <DeleteReview setReviews={setReviews} id={review.id}/>
          </div>
        ))
      )}

      <AddReview setReviews={setReviews} isbn={isbn} />
    </div>
  );
}

export default ReviewList;
