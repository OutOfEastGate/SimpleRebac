
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
    path: "/",
    element:<Navigate to="/homepage"/>
  },
  {
    path: "/",
    element:<HomePage/>,
    children: [
      {
        path: "/admin/appSelect",
        element: <AppSelect></AppSelect>
      },
      {
        path: "/admin/homepage",
        element: <FirstPage/>
      },
      {
        path: "/admin/space",
        element: <Store/>
      },
      {
        path: "/show/permission",
        element: <Relation></Relation>
      },
      {
        path: "/show/relation",
        element: <GraphHome/>
      },
      {
        path: "/admin/system",
        element: <System/>
      },
      {
        path: "/key",
        element: <Key></Key>
      },
      {
        path: "/admin/model",
        element: <Model></Model>
      }
    ]
  },
]
export default routes
