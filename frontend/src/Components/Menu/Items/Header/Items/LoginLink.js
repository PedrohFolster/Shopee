import React from 'react';
import { Link } from 'react-router-dom';
import '../Header.css';
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const LoginLink = ({ activeLink, handleSetActive }) => (
  <Link
    to="/login"
    aria-label="Login"
    className={`auth-link ${activeLink === "login" ? "active" : ""}`}
    onClick={() => handleSetActive("login")}
  >
    <FontAwesomeIcon icon={faUser} className="icon" />
    Entrar
  </Link>
);

export default LoginLink;
