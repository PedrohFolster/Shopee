import React, { useState } from 'react';
import axios from 'axios';
import ProdutosList from '../../Components/Produto/ProdutosList';
import '../CSS/Carrinho.css';

const Carrinho = () => {
  const [carrinho, setCarrinho] = useState(
    JSON.parse(localStorage.getItem('carrinho'))?.map(produto => ({
      ...produto,
      quantidade: produto.quantidade || 1
    })) || []
  );

  const aumentarQuantidade = (id) => {
    const novoCarrinho = carrinho.map(produto => 
      produto.id === id ? { ...produto, quantidade: produto.quantidade + 1 } : produto
    );
    setCarrinho(novoCarrinho);
    localStorage.setItem('carrinho', JSON.stringify(novoCarrinho));
  };

  const diminuirQuantidade = (id) => {
    const novoCarrinho = carrinho.map(produto => 
      produto.id === id && produto.quantidade > 1 ? { ...produto, quantidade: produto.quantidade - 1 } : produto
    );
    setCarrinho(novoCarrinho);
    localStorage.setItem('carrinho', JSON.stringify(novoCarrinho));
  };

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
      <ProdutosList 
        produtos={carrinho} 
        aumentarQuantidade={aumentarQuantidade} 
        diminuirQuantidade={diminuirQuantidade} 
      />
      <button className="finalizar-button" onClick={finalizarCompra}>Finalizar Compra</button>
    </div>
  );
};

export default Carrinho;