import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import CriarUsuario from './Pages/testando';
import './App.css';
import Home from './Pages/Home';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/home" element={<Home />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
