import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import CriarUsuario from './Pages/testando';
import Header from './Components/Menu/Items/Header/Header';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<CriarUsuario />} />
          <Route path="/home" element={<Header />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
