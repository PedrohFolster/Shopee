import React from 'react';
import { SearchInput } from '../HeaderStyles'; // Certifique-se de que o estilo está importado corretamente

const SearchInputComponent = () => {
  return (
    <div className="search-container">
      <SearchInput
        type="text"
        placeholder="Buscar..."
        aria-label="Campo de busca"
      />
    </div>
  );
};

export default SearchInputComponent;
