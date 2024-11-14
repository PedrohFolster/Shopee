import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../Header.css';

const SearchInputComponent = ({ hidden }) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResults, setSearchResults] = useState([]);

  useEffect(() => {
    if (searchTerm) {
      const fetchLojas = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/lojas/search?nome=${searchTerm}`);
          setSearchResults(response.data);
        } catch (error) {
          console.error('Erro ao buscar lojas:', error);
        }
      };
      fetchLojas();
    } else {
      setSearchResults([]);
    }
  }, [searchTerm]);

  return (
    <div className="search-container">
      <input
        className="search-input"
        type="text"
        placeholder="Buscar lojas..."
        aria-label="Campo de busca"
        hidden={hidden}
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />
      {searchResults.length > 0 && (
        <div className="search-dropdown">
          {searchResults.map((loja) => (
            <div key={loja.id} className="search-item">
              {loja.nome}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default SearchInputComponent;