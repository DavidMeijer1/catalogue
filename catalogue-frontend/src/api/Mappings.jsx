import axios from "axios";

export const backendApi = {
  login,
  userinfo,
  addBook
};

function login(username, password) {
  return baseURL.post("/auth/login", username, password, {
    headers: { "Content-Type": "application/json" },
  });
}

function userinfo(user) {
  return baseURL.get("api/v1/users/me", {
    headers: { Authorization: bearerAuth(user) },
  });
}

function addBook(user, formData) {
  return baseURL.post("/api/v1/books", formData, {
    headers: {
      Authorization: bearerAuth(user),
      "Content-type": "application/json",
    },
  });
}

// -- Axios calls

const baseURL = axios.create({
  baseURL: "http://localhost:8080",
});

baseURL.interceptors.request.use(
  function (config) {
    // If token is expired, redirect user to login
    if (config.headers.Authorization) {
      const token = config.headers.Authorization.split(" ")[1];
      const data = parseJwt(token);
      if (Date.now() > data.exp * 1000) {
        window.location.href = "/";
      }
    }
    return config;
  },
  function (error) {
    return Promise.reject(error);
  }
);

// -- Helper functions

function bearerAuth(user) {
  return `Bearer ${user.accessToken}`;
}

export function parseJwt(token) {
  if (!token) {
    return;
  }
  const base64Url = token.split(".")[1];
  const base64 = base64Url.replace("-", "+").replace("_", "/");
  return JSON.parse(window.atob(base64));
}

export const handleLogError = (error) => {
  if (error.response) {
    console.log(error.response.data);
  } else if (error.request) {
    console.log(error.request);
  } else {
    console.log(error.message);
  }
};
