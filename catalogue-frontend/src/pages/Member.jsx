import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";

import Header from "../components/header/Header";

function Member() {
  const { id } = useParams();
  const [user, setUser] = useState([]);

  useEffect(() => {
    const fetchUser = async () => {
      const reponse = await fetch(`http://localhost:8080/api/v1/users/${id}`);
      const data = await reponse.json();
      setUser(data);
    };
    fetchUser();
  }, [id]);

  return (
    <div>
      <Header />
      Member:
      {user.username}
    </div>
  );
}

export default Member;
