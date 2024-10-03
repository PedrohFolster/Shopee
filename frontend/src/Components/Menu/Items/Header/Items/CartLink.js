import React from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faShoppingCart } from "@fortawesome/free-solid-svg-icons";
import '../Header.css';

const CartLink = ({ activeLink, handleSetActive }) => (
  <Link
    to="/carrinho"
    aria-label="Abrir carrinho"
    className={`auth-link ${activeLink === "carrinho" ? "active" : ""}`}
    onClick={() => handleSetActive("carrinho")}
  >
    <FontAwesomeIcon icon={faShoppingCart} className="icon" />
  </Link>
);

export default CartLink;