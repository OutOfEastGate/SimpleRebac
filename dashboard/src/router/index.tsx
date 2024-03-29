
import GraphHome from "../components/graph/Home";
import FirstPage from "../components/homepage";
import HomePage from "../page/HomePage";
import {Navigate} from "react-router-dom";
import System from "../components/system";
import Key from "../components/key";
import Model from "../components/model";


const routes = [
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
