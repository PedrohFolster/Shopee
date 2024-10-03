import React from 'react';
import { Link } from 'react-router-dom';
import '../Header.css';
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const FavoritesLink = ({ activeLink, handleSetActive }) => (
  <Link
    to="/favoritos"
    aria-label="Favoritos"
    className={`icon-link ${activeLink === "favoritos" ? "active" : ""}`}
    onClick={() => handleSetActive("favoritos")}
  >
    <FontAwesomeIcon icon={faHeart} className="icon" />
  </Link>
);

export default FavoritesLink;
