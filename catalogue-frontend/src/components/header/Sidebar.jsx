import { useState } from "react";
import { Link } from "react-router-dom";

import { useAuth } from "../../api/AuthContext";

const Sidebar = () => {
  const { userIsAuthenticated } = useAuth();
  const [isOpen, setIsOpen] = useState(false);

  const toggleSidebar = () => {
    setIsOpen(!isOpen);
  };

  return (
    <div
      className={`fixed top-0 left-0 h-full w-[200px] bg-gray-100 p-5 transition-all duration-300 ${
        isOpen ? "translate-x-0" : "-translate-x-full"
      }`}
    >
      <button
        className="absolute top-2 right-[-40px] bg-blue-500 px-2 py-1 text-2xl"
        onClick={toggleSidebar}
      >
        {isOpen ? "×" : "☰"}
      </button>
      <ul className="flex flex-col space-y-3">
        {userIsAuthenticated() ? (
          <>
            <li>
              <Link to="/"
                className="text-gray-600 hover:text-gray-800"
              >
                Home
              </Link>
            </li>
            <li>
              <Link
                to="/books/mybooks"
                className="text-gray-600 hover:text-gray-800"
              >
                Books
              </Link>
            </li>
            <li>
              <Link
                to="/group"
                className="text-gray-600 hover:text-gray-800"
              >
                Group
              </Link>
            </li>
            <li>
              <Link
                to="/userprofile"
                className="text-gray-600 hover:text-gray-800"
              >
                Profile
              </Link>
            </li>
            <li>
              <Link
                to="/books/ownbooks"
                className="text-gray-600 hover:text-gray-800"
              >
                Own books
              </Link>
            </li>
          </>
        ) : (
          <>
            <li>
              <Link
                to="/"
                className="text-gray-600 hover:text-gray-800"
              >
                Home
              </Link>
            </li>
          </>
        )}
      </ul>
    </div>
  );
};

export default Sidebar;
