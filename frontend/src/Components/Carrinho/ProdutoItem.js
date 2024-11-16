import React from 'react';
import '../../Pages/CSS/Produto.css';

const ProdutoItem = ({ produto, aumentarQuantidade, diminuirQuantidade, removerProduto }) => {
  const handleRemoverClick = () => {
    if (window.confirm(`Deseja realmente remover ${produto.nome} do carrinho?`)) {
      removerProduto(produto.id);
    }
  };

  return (
    <div className="produto-item">
      <button className="remover-button" onClick={handleRemoverClick}>✖</button>
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
};

export default ProdutoItem;