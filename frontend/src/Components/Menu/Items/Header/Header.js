import React, { useState, useContext } from 'react';
import './Header.css';
import SearchInputComponent from './Items/SearchInput';
import LoginLink from './Items/LoginLink';
import RegisterLink from './Items/RegisterLink';
import MinhaContaLink from './Items/MinhaContaLink'; 
import CartLink from './Items/CartLink';
import NavBar from '../NavBar/NavBar';
import { AuthContext } from '../../../../Util/Authentication';

const Header = ({ searchHidden = false, navbarHidden = true }) => {
  const [activeLink, setActiveLink] = useState("");
  const { isAuthenticated } = useContext(AuthContext);

  const handleSetActive = (link) => {
    setActiveLink(link);
  };

  return (
    <>
      <header className="header">
        <div className="header-content">
          <div className="header-left">
           
            <a href="/" className="logo-link" aria-label="Página inicial KaBuM!">
              <img alt="Logo Kabum" src="https://static.kabum.com.br/conteudo/icons/logo.svg" width="105" height="36" />
            </a>
          </div>
          {!searchHidden && (
            <div className="search-container">
              <SearchInputComponent />
            </div>
          )}
          <div className="header-right">
            {isAuthenticated ? (
              <MinhaContaLink
                activeLink={activeLink}
                handleSetActive={handleSetActive}
              />
            ) : (
              <>
                <LoginLink
                  activeLink={activeLink}
                  handleSetActive={handleSetActive}
                />
                <span>|</span>
                <RegisterLink
                  activeLink={activeLink}
                  handleSetActive={handleSetActive}
                />
              </>
            )}
            <CartLink
              activeLink={activeLink}
              handleSetActive={handleSetActive}
            />
          </div>
        </div>
      </header>
      {!navbarHidden && <NavBar />}
    </>
  );
};

export default Header;