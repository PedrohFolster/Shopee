import React, { useState } from 'react';
import '../../Pages/CSS/Produto.css';
import ModalConfirmacao from './ModalConfirmacao';

const ProdutoItem = ({ produto, aumentarQuantidade, diminuirQuantidade, removerProduto }) => {
  const [isModalOpen, setModalOpen] = useState(false);

  const handleRemoverClick = () => {
    setModalOpen(true);
  };

  const handleConfirmRemove = () => {
    removerProduto(produto.id);
    setModalOpen(false);
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
      <ModalConfirmacao
        isOpen={isModalOpen}
        onClose={() => setModalOpen(false)}
        onConfirm={handleConfirmRemove}
        message={`Deseja realmente remover este item do carrinho?`}
      />
    </div>
  );
};

export default ProdutoItem;