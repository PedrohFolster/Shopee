import React from 'react';
import ProdutoItem from './ProdutoItem';
import '../../Pages/CSS/Produto.css';

const ProdutosList = ({ produtos, aumentarQuantidade, diminuirQuantidade, removerProduto }) => (
    <div className="produtos-list">
      {produtos.length > 0 ? (
        produtos.map((produto, index) => (
          <ProdutoItem
            key={index}
            produto={produto}
            aumentarQuantidade={aumentarQuantidade}
            diminuirQuantidade={diminuirQuantidade}
            removerProduto={removerProduto}
          />
        ))
      ) : (
        <p>Nenhum produto no carrinho.</p>
      )}
    </div>
  );
  
  export default ProdutosList;