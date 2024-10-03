import React, { useState } from 'react';
import '../Header.css';

const MenuButton = () => {
  const [isOpen, setIsOpen] = useState(false);

  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  return (
    <button 
      className={`menu-button ${isOpen ? 'open' : ''}`} 
      onClick={toggleMenu}
      aria-label={isOpen ? "Fechar menu" : "Abrir menu"}
      aria-expanded={isOpen}
    >
      <span className="line"></span>
      <span className="line"></span>
      <span className="line"></span>
    </button>
  );
};

export default MenuButton;