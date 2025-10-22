import axios from "axios";
import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

import { useAuth } from "../../api/AuthContext";
import { parseJwt } from "../../api/Mappings";

function LogIn() {
  const { getUsername, userLogin, userLogout, userIsAuthenticated } = useAuth();
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(""); // Reset error message

    try {
      const response = await axios.post(
        "http://localhost:8080/auth/login",
        {
          username,
          password,
        },
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      const { accessToken } = response.data;
      const data = parseJwt(accessToken);
      const authenticatedUser = { data, accessToken, username };
      userLogin(authenticatedUser);
      setUsername(""); // Reset username
      setPassword(""); // Reset password
    } catch (error) {
      setError("Login failed. Please check your username and password.");
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () =>{
    userLogout();
    navigate("/");
  }

  return (
    <div className="login-container">
      {userIsAuthenticated() ? (
        <>
          <p>Welcome,<Link to="/userprofile"> {getUsername()} </Link>!</p>
          <button onClick={handleLogout} className="logout-button">
            Log Out
          </button>
        </>
      ) : (
        <form className="flex items-center" onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="Username"
            required
            value={username}
            onChange={(event) => setUsername(event.target.value)}
            aria-label="Username"
          />
          <input
            type="password"
            placeholder="Password"
            required
            value={password}
            onChange={(event) => setPassword(event.target.value)}
            aria-label="Password"
          />
          <div>
            <button type="submit" className="border-rose-900 border-2">
              Log in
            </button>
          </div>
          <button
            className="border-green-700 border-2"
            type="button"
            onClick={() => navigate("/register")}
          >
            Create account
          </button>
          {error && <p className="text-red-500">{error}</p>}
          {loading && <p>Loading...</p>}
        </form>
      )}
    </div>
  );
}

export default LogIn;
