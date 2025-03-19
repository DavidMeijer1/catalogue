import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Route, Routes } from "react-router-dom";

import Main from "./Main.jsx";
import StartPage from "./pages/main/StartPage.jsx";
import Book from "./pages/book/Book.jsx";
import BookList from "./pages/book/BookList.jsx";
import OwnBooks from "./pages/book/OwnBooks.jsx";

import Register from "./pages/Register.jsx";
import Group from "./pages/Group.jsx";
import Member from "./pages/Member.jsx";
import UserProfile from "./pages/UserProfile.jsx";
import { AuthProvider } from "./api/AuthContext.jsx";
import PrivateRoute from "./api/PrivateRoute.jsx";
import "./index.css";

createRoot(document.getElementById("root")).render(
  <AuthProvider>
    <StrictMode>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<StartPage />}></Route>
          <Route path="/register" element={<Register />}></Route>
          <Route path="/books/:isbn" element={<Book />}></Route>
          <Route path="/books/mybooks" element={<BookList />}></Route>
          <Route path="/books/ownbooks" element={<OwnBooks />}></Route>
          <Route path="/group" element={<Group />}></Route>
          <Route path="/group/:id" element={<Member />}></Route>
          <Route path="/userprofile" element={<PrivateRoute><UserProfile /></PrivateRoute>}></Route>
        </Routes>
      </BrowserRouter>
    </StrictMode>
  </AuthProvider>
);
