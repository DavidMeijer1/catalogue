import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import Header from "../components/header/Header";

function Group() {
  const [groupList, setGroupList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchGroup = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/v1/users`);
        const result = await response.json();
        console.log(result);
        setGroupList(result);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchGroup();
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <>
      <Header />
      <h1 className="mt-20 h-20 text-gray-100 text-5xl">My Members</h1>

      <div className="w-full flex flex-col space-y-4 items-center">
        {groupList.map((member) => (
          <Link to={"/group/" + member.id}key={member.id} className="w-full flex justify-center">
            <article className="w-8/12 h-20 md:mx-4 flex flex-row justify-between items-center bg-white rounded-lg shadow-lg p-4 border-gray-200 transition-transform transform duration-300 ease-in-out hover:scale-110 hover:bg-gray-100 text-gray-900">
              <div className="flex items-center md:w-48">
                <p>Username: {member.username}</p>
              </div>
            </article>
          </Link>
        ))}
      </div>
    </>
  );
}

export default Group;
