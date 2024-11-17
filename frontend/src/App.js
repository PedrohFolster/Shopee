import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ProtectedRoute from './Util/ProtectedRoute';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import Home from './Pages/HTML/Home';
import Register from './Pages/HTML/Register';
import Login from './Pages/HTML/Login';
import CreateLoja from './Pages/HTML/CreateLoja';
import MinhaLoja from './Pages/HTML/MinhaLoja';
import Carrinho from './Pages/HTML/Carrinho';
import Produto from './Pages/HTML/Produto';
import EditarPerfil from './Pages/HTML/EditarPerfil';
import LojaPage from './Pages/HTML/LojaPage';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/home" element={<Home />} />
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
          <Route path="/produto/:id" element={<Produto />} />
          <Route path="/carrinho" element={<Carrinho />} />
          <Route path="/CreateLoja" element={<ProtectedRoute element={<CreateLoja />} />} />
          <Route path="/MinhaLoja" element={<ProtectedRoute element={<MinhaLoja />} />} />
          <Route path="/editar-perfil" element={<ProtectedRoute element={<EditarPerfil />} />} />
          <Route path="/loja/:id" element={<LojaPage />} />
        </Routes>
        <ToastContainer position="bottom-right" autoClose={3000} />
      </div>
    </Router>
  );
}

export default App;
