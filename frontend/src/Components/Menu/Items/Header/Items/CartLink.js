import React from 'react';
import { Link } from 'react-router-dom';
import { AuthLink, Icon } from '../HeaderStyles';
import { faShoppingCart } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const CartLink = ({ activeLink, handleSetActive }) => (
  <Link
    as={Link}
    to="/carrinho"
    aria-label="Abrir carrinho"
    className={activeLink === "carrinho" ? "active" : ""}
    onClick={() => handleSetActive("carrinho")}
  >
    <Icon>
      <FontAwesomeIcon icon={faShoppingCart} />
    </Icon>
  </Link>
);

export default CartLink;
