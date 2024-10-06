import React from 'react';
import { Link } from 'react-router-dom';
import '../Header.css';

const RegisterLink = ({ activeLink, handleSetActive }) => (
  <Link
    to="/register"
    aria-label="Registro"
    className={`auth-link ${activeLink === "registro" ? "active" : ""}`}
    onClick={() => handleSetActive("registro")}
  >
    Cadastre-se
  </Link>
);

export default RegisterLink;
