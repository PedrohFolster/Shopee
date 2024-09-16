import React, { useState } from 'react';
import { MenuButton as MenuButtonStyles } from '../HeaderStyles'; // Corrija a importação

const MenuButton = () => {
  const [isOpen, setIsOpen] = useState(false); // Estado para gerenciar o menu aberto

  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  return (
    <MenuButtonStyles
      aria-label="Abrir menu"
      aria-expanded={isOpen}
      className={isOpen ? 'open' : ''}
      onClick={toggleMenu}
    >
      <span className="line"></span>
      <span className="line"></span>
      <span className="line"></span>
    </MenuButtonStyles>
  );
};

export default MenuButton;
