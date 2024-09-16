import React from 'react';
import { Link } from 'react-router-dom';
import { AuthLink, Icon } from '../HeaderStyles';
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const LoginLink = ({ activeLink, handleSetActive }) => (
  <AuthLink
    as={Link}
    to="/login"
    aria-label="Login"
    className={activeLink === "login" ? "active" : ""}
    onClick={() => handleSetActive("login")}
  >
    <Icon>
      <FontAwesomeIcon icon={faUser} />
    </Icon>
    Entrar
  </AuthLink>
);

export default LoginLink;
