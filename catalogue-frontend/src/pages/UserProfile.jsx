import React from "react";
import { useAuth } from "../api/AuthContext";
import { useState } from "react";
import axios from "axios";

import Header from "../components/header/Header";

function UserProfile() {
  const {
    getUser,
    getUsername,
    getAccountType,
    userLogout,
    userIsAuthenticated,
  } = useAuth();
  const user = getUser();
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  const handlePasswordSubmit = async (event) => {
    event.preventDefault();

    const payload = {
      newPassword: password,
    };

    try {
      const response = await axios.patch(
        "http://localhost:8080/api/v1/users/update",
        payload,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${user.accessToken}`,
          },
        }
      );

      if (response.status === 200) {
        setMessage("Password updated successfully!");
      } else {
        setMessage("Failed to update password.");
      }
    } catch (error) {
      console.error(error);
      setMessage("An error occurred while updating the password.");
    }
  };

  return (
    <div className="user-profile">
      <Header />
      {userIsAuthenticated() ? (
        <>
          <h1>Welcome {getUsername()}!</h1>
          <div>{getUser().data.id}</div>
          <h2>Account Type: {getAccountType()}</h2>
          <h3>Change password:</h3>
          <form onSubmit={handlePasswordSubmit}>
            <input
              type="password"
              placeholder="New password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
              required
            />
            <button type="submit">Submit</button>
          </form>
          {message && <div>{message}</div>}
          <div>
            <button onClick={userLogout} className="logout-button">
              Log Out
            </button>
          </div>
        </>
      ) : (
        <h1>Please log in.</h1>
      )}
    </div>
  );
}

export default UserProfile;
