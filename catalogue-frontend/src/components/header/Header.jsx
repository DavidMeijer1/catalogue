import Sidebar from "./Sidebar";
import LogIn from "./LogIn";


function Header() {
  return (
    <header className="">
      <div className="fixed top-6 left-20">
        <a href="/" className="text-4xl font-bold" aria-label="Home">
          Book Club Catalogue
        </a>
      </div>
      <div className="fixed top-6 right-10">
        <LogIn />
      </div>
      <div className="fixed top-6 left-6">
        <Sidebar />
      </div>
    </header>
  );
}


export default Header;
