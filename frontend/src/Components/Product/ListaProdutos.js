import React from 'react';
import PropTypes from 'prop-types';
import ProdutoHome from './ProdutoHome';

const ListaProdutos = ({ produtosFiltrados, adicionarAoCarrinho, handleProdutoClick }) => {
  return (
    <div className='produtos-list'>
      {produtosFiltrados.length > 0 ? (
        produtosFiltrados.map(produto => (
          <ProdutoHome
            key={produto.id}
            produto={produto}
            onAddToCart={adicionarAoCarrinho}
            onNameClick={() => handleProdutoClick(produto.id)}
          />
        ))
      ) : (
        <p>Nenhum produto encontrado.</p>
      )}
    </div>
  );
};

ListaProdutos.propTypes = {
  produtosFiltrados: PropTypes.array.isRequired,
  adicionarAoCarrinho: PropTypes.func.isRequired,
  handleProdutoClick: PropTypes.func.isRequired,
};

export default ListaProdutos; 