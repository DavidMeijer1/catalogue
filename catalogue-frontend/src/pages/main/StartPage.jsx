import React from "react";

import Header from "../../components/header/Header";

function StartPage() {
  return (
    <div>
      <Header />
      <div className="flex justify-center items-center">
        <div className="text-center w-2/5">
          <p>
            Welcome to Our book club! Dear book lovers, we are thrilled to have
            you join our community of avid readers! Whether you are a lifelong
            bibliophile or just starting your literary journey, this book club
            is a space for everyone to share their love of books, explore new
            genres, and engage in thoughtful discussions.

          </p>

          <p>
            Thank you for being a part of our book-loving community. We can’t
            wait to dive into our first book together and hear your thoughts!
            Happy Reading!{" "}
          </p>
        </div>
      </div>
    </div>
  );
}

export default StartPage;
