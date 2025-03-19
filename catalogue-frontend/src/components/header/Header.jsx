import Sidebar from "./Sidebar";
import LogIn from "./LogIn";

function Header() {
  return (
    <>
      <div>
        <a>
          <div className="fixed top-6 left-20 text-4xl font-bold">Book Club Catalogue</div>
        </a>
      </div>
      <div className="fixed top-6 right-10">
        <LogIn />
      </div>
      <div className="fixed top-6 left-6">
        <Sidebar />
      </div>
    </>
  );
}

export default Header;
