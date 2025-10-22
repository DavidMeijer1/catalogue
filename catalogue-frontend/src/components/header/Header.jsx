import Sidebar from "./Sidebar";
import LogIn from "./LogIn";


function Header() {
  return (
    <header className="absolute top-0 left-0 w-full z-10">
      <div className="flex justify-between items-center pl-20 p-4">
        <a href="/" className="text-5xl font-bold" aria-label="Home">
          Book Club Catalogue
        </a>
      </div>
      <div className="fixed top-6 right-10">
        <LogIn />
      </div>
      <div className="top-6 left-6">
        <Sidebar />
      </div>
    </header>
  );
}


export default Header;
