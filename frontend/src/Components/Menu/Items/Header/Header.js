import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser, faShoppingCart, faHeart } from '@fortawesome/free-solid-svg-icons';
import { Header as HeaderStyles, HeaderContent, HeaderLeft, HeaderRight, LogoLink, SearchContainer, AuthButton, Icon } from './HeaderStyles'; // Corrija as importações conforme necessário
import MenuButtonComponent from './Items/MenuButton';
import SearchInputComponent from './Items/SearchInput';
import LoginLink from './Items/LoginLink';
import RegisterLink from './Items/RegisterLink';
import CartLink from './Items/CartLink';
import FavoritesLink from './Items/FavoritesLink';

const Header = () => {
  const [activeLink, setActiveLink] = useState("");

  const handleSetActive = (link) => {
    setActiveLink(link);
  };

  return (
    <HeaderStyles>
      <HeaderContent>
        <HeaderLeft>
          <MenuButtonComponent />
          <LogoLink href="/" aria-label="Página inicial KaBuM!">
            <img alt="Logo Kabum" src="https://static.kabum.com.br/conteudo/icons/logo.svg" width="105" height="36" />
          </LogoLink>
        </HeaderLeft>
        <SearchContainer>
          <SearchInputComponent />
        </SearchContainer>
        <HeaderRight>
          <LoginLink aria-label="Entre" className={activeLink === "login" ? "active" : ""} onClick={() => handleSetActive("login")}>
            <FontAwesomeIcon icon={faUser} size="lg" className="icon" /> Entre
          </LoginLink>
          <span>|</span>
          <RegisterLink aria-label="Cadastre-se" className={activeLink === "register" ? "active" : ""} onClick={() => handleSetActive("register")}>
            Cadastre-se
          </RegisterLink>
          <CartLink aria-label="Abrir carrinho" className={activeLink === "carrinho" ? "active" : ""} onClick={() => handleSetActive("carrinho")}>
            <FontAwesomeIcon icon={faShoppingCart} size="lg" className="icon" />
          </CartLink>
          <FavoritesLink aria-label="Favoritos" className={activeLink === "favoritos" ? "active" : ""} onClick={() => handleSetActive("favoritos")}>
            <FontAwesomeIcon icon={faHeart} size="lg" className="icon" />
          </FavoritesLink>
        </HeaderRight>
      </HeaderContent>
    </HeaderStyles>
  );
};

export default Header;
