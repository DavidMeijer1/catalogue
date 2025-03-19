import React from "react";
import { useAuth } from "../api/AuthContext";

import Header from "../components/header/Header";

function UserProfile() {
  const {
    getUser,
    getUsername,
    getAccountType,
    userLogout,
    userIsAuthenticated,
  } = useAuth();

  return (
    <div className="user-profile">
      <Header />
      {userIsAuthenticated() ? ( 
        <>
          <h1>Welcome {getUsername()}!</h1>
          <div>{getUser().data.id}</div>
          <h2>Account Type: {getAccountType()}</h2>
          <div>
            <button onClick={userLogout} className="logout-button">
              {" "}
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
