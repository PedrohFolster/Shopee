import React from 'react';
import PropTypes from 'prop-types';
import ProdutoLoja from '../../Components/Product/ProdutoLoja';
import Button from '../../Components/Button/Button';

const ProdutosList = ({ produtos, onEdit, onAddNew, searchTerm, setSearchTerm }) => {
  const filteredProdutos = produtos.filter(produto =>
    produto.nome.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="produtos-container">
      <div className="produtos-header">
        <Button type="button-register" onClick={onAddNew}>
          Cadastrar Novo Produto
        </Button>
        <input
          type="text"
          placeholder="Pesquisar produtos..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="search-bar"
        />
      </div>
      <div className="minha-loja-produtos-list">
        {filteredProdutos.length === 0 ? (
          <p>Nenhum produto encontrado</p>
        ) : (
          filteredProdutos.map(produto => (
            <ProdutoLoja
              key={produto.id}
              produto={produto}
              onEdit={onEdit}
              pageStyle="minha-loja-style"
            />
          ))
        )}
      </div>
    </div>
  );
};

ProdutosList.propTypes = {
  produtos: PropTypes.array.isRequired,
  onEdit: PropTypes.func.isRequired,
  onAddNew: PropTypes.func.isRequired,
  searchTerm: PropTypes.string.isRequired,
  setSearchTerm: PropTypes.func.isRequired,
};

export default ProdutosList; 