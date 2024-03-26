import React from 'react';
import {useRoutes} from "react-router-dom";
import router from "./router";


function BeforeEnter() {

  return useRoutes(router);
}

function App() {
  return (
      <BeforeEnter></BeforeEnter>
  );
}

export default App;
