import React from 'react';
import '../Header.css';

const SearchInputComponent = ({ hidden }) => {
  return (
    <div className="search-container">
      <input
        className="search-input"
        type="text"
        placeholder="Buscar..."
        aria-label="Campo de busca"
        hidden={hidden}
      />
    </div>
  );
};

export default SearchInputComponent;