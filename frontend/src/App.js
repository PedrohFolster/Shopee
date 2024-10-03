import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import CriarUsuario from './Pages/testando';
import Home from './Pages/Home';
import Register from './Pages/Register';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/home" element={<Home />} />
          <Route path="/register" element={<Register />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
