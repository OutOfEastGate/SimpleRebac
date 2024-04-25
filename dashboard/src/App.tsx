import React from 'react';
import {useLocation, useNavigate, useRoutes} from "react-router-dom";
import router from "./router";




function App() {
  const navigateTo = useNavigate()
  function BeforeEnter() {
    const location = useLocation()
    //检查是否登陆
    let token = localStorage.getItem("token")
    if (location.pathname !== "/login" && token === null) {
        navigateTo("/login")
    }
    return useRoutes(router);
  }
  return (
      <BeforeEnter></BeforeEnter>
  );
}

export default App;
