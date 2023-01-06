import React from "react";
import {BrowserRouter,Routes,Route, Outlet } from "react-router-dom";
import Upload from "./Upload";

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<div>This is the basic layout component <Outlet/></div>}>
          <Route index element={<div>Welcome to the Homepage</div>} />
          <Route path="upload" element={<Upload/>} />
          <Route path="*" element={<div>That page does not exist</div>} />
        </Route>
      </Routes>
    </BrowserRouter>
  
  );
}

export default App;