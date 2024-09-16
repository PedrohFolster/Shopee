import React from 'react';
import { Link } from 'react-router-dom';
import { AuthLink, Icon } from '../HeaderStyles';
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const FavoritesLink = ({ activeLink, handleSetActive }) => (
  <Link
    as={Link}
    to="/favoritos"
    aria-label="Favoritos"
    className={activeLink === "favoritos" ? "active" : ""}
    onClick={() => handleSetActive("favoritos")}
  >
    <Icon>
      <FontAwesomeIcon icon={faHeart} />
    </Icon>
  </Link>
);

export default FavoritesLink;
