import React, { useState } from 'react';
import axios from 'axios';
import '../CSS/Carrinho.css';

const Carrinho = () => {
  const [carrinho, setCarrinho] = useState(JSON.parse(localStorage.getItem('carrinho')) || []);

  const finalizarCompra = () => {
    axios.post('http://localhost:8080/produtos/finalizar-compra', carrinho)
      .then(response => {
        alert(response.data);
        localStorage.removeItem('carrinho');
        setCarrinho([]);
      })
      .catch(error => {
        console.error('Erro ao finalizar compra:', error);
        alert('Erro ao finalizar compra.');
      });
  };

  return (
    <div className="carrinho-container">
      <h2 className="carrinho-title">Carrinho</h2>
      <div className="produtos-list">
        {carrinho.length > 0 ? (
          carrinho.map((produto, index) => (
            <div key={index} className="produto-item">
              <img src={produto.imagem || 'default-image.jpg'} alt={produto.nome} />
              <h2>{produto.nome}</h2>
              <p className="preco">R$ {produto.preco.toFixed(2)}</p>
            </div>
          ))
        ) : (
          <p>Nenhum produto no carrinho.</p>
        )}
      </div>
      <button className="finalizar-button" onClick={finalizarCompra}>Finalizar Compra</button>
    </div>
  );
};

export default Carrinho;