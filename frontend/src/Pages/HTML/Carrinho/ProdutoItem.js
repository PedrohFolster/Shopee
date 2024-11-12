import React from 'react';
import '../../CSS/Carrinho.css';

const ProdutoItem = ({ produto, aumentarQuantidade, diminuirQuantidade }) => (
  <div className="produto-item">
    <img src={produto.imagem || 'default-image.jpg'} alt={produto.nome} />
    <h2>{produto.nome}</h2>
    <p className="preco">R$ {produto.preco.toFixed(2)}</p>
    <div className="quantidade-container">
      <button onClick={() => diminuirQuantidade(produto.id)}>-</button>
      <span>{produto.quantidade}</span>
      <button onClick={() => aumentarQuantidade(produto.id)}>+</button>
    </div>
  </div>
);

export default ProdutoItem;