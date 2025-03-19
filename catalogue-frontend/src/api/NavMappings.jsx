import StartPage from "../pages/main/StartPage";
import BookList from "../pages/book/BookList";
import Group from "../pages/Group";
import UserProfile from "../pages/UserProfile";

export const accountType = {
  admin: "ADMINISTRATOR",
  user: "USER",
  visitor: "VISITOR",
};

const allLoggedInTypes = [accountType.admin, accountType.user];
const allAccountTypes = [accountType.visitor, ...allLoggedInTypes];

export const navList = [
  {
    path: "/",
    name: StartPage,
    allowedAccountTypes: allAccountTypes,
    hideFromMenu: false,
    element: () => <StartPage />,
  },
  {
    path: "/books/mybooks",
    name: BookList,
    allowedAccountTypes: allLoggedInTypes,
    hideFromMenu: false,
    element: () => <BookList />,
  },
  {
    path: "/group",
    name: Group,
    allowedAccountTypes: allLoggedInTypes,
    hideFromMenu: false,
    element: () => <Group />,
  },
  {
    path: "/userprofile",
    name: UserProfile,
    allowedAccountTypes: allAccountTypes,
    hideFromMenu: false,
    element: () => <UserProfile />,
  },
];
