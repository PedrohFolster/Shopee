import React from 'react';
import '../Header.css';

const SearchInputComponent = () => {
  return (
    <div className="search-container">
      <input
        className="search-input"
        type="text"
        placeholder="Buscar..."
        aria-label="Campo de busca"
      />
    </div>
  );
};

export default SearchInputComponent;