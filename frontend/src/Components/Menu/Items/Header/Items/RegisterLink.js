import React from 'react';
import { Link } from 'react-router-dom';
import { AuthLink } from '../HeaderStyles';

const RegisterLink = ({ activeLink, handleSetActive }) => (
  <AuthLink
    as={Link}
    to="/registro"
    aria-label="Registro"
    className={activeLink === "registro" ? "active" : ""}
    onClick={() => handleSetActive("registro")}
  >
    Cadastre-se
  </AuthLink>
);

export default RegisterLink;
