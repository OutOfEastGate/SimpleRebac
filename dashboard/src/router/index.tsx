
import GraphHome from "../components/graph/Home";
import FirstPage from "../components/homepage";
import HomePage from "../page/HomePage";
import {Navigate} from "react-router-dom";
import System from "../components/system";
import Key from "../components/key";
import Model from "../components/model";
import Store from "../components/store";
import Relation from "../components/relation";
import LoginPage from "../page/LoginPage";
import AppSelect from "../page/AppSelect";


const routes = [
  {
    path: "/login",
    element: <LoginPage></LoginPage>
  },
  {
    path: "/appSelect",
    element: <AppSelect></AppSelect>
  },
  {
    path: "/",
    element:<Navigate to="/homepage"/>
  },
  {
    path: "/",
    element:<HomePage/>,
    children: [
      {
        path: "/homepage",
        element: <FirstPage/>
      },
      {
        path: "/space",
        element: <Store/>
      },
      {
        path: "/permission",
        element: <Relation></Relation>
      },
      {
        path: "/relation",
        element: <GraphHome/>
      },
      {
        path: "/system",
        element: <System/>
      },
      {
        path: "/key",
        element: <Key></Key>
      },
      {
        path: "/model",
        element: <Model></Model>
      }
    ]
  },
]
export default routes
