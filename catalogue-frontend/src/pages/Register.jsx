import { useState } from "react";
import axios, { HttpStatusCode } from "axios";

import Header from "../components/header/Header";
import { useAuth } from "../api/AuthContext";

function Register() {
  const { userIsAuthenticated } = useAuth();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:8080/auth/register",
        { username, password },
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      if (response.status === HttpStatusCode.Created) {
        alert("Succesfully created an account!");
      }
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div>
      <Header />
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(event) => setUsername(event.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(event) => setPassword(event.target.value)}
          required
        />
        <button type="submit">Register</button>
      </form>
    </div>
  );
}

export default Register;
