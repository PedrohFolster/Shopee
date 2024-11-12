import React from 'react';
import ProdutoItem from './ProdutoItem';
import '../../CSS/Carrinho.css';

const ProdutosList = ({ produtos, aumentarQuantidade, diminuirQuantidade }) => (
    <div className="produtos-list">
      {produtos.length > 0 ? (
        produtos.map((produto, index) => (
          <ProdutoItem
            key={index}
            produto={produto}
            aumentarQuantidade={aumentarQuantidade}
            diminuirQuantidade={diminuirQuantidade}
          />
        ))
      ) : (
        <p>Nenhum produto no carrinho.</p>
      )}
    </div>
  );
  
  export default ProdutosList;