import React from 'react';
import { Link } from 'react-router-dom';
import logo from './logo.svg';

const Logo = () => {
  return (
    <div className="header-logo">
      <Link to="/home">
        <img src={logo} alt="Logo" />
      </Link>
    </div>
  );
};

export default Logo;
