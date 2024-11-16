import React, { useState } from 'react';
import axios from 'axios';
import ProdutosList from '../../Components/Carrinho/ProdutosList';
import Header from '../../Components/Menu/Items/Header/Header';
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

  const removerProduto = (id) => {
    const novoCarrinho = carrinho.filter(produto => produto.id !== id);
    setCarrinho(novoCarrinho);
    localStorage.setItem('carrinho', JSON.stringify(novoCarrinho));
  };

  const verificarProdutosAtivos = async () => {
    try {
      const promises = carrinho.map(produto =>
        axios.get(`http://localhost:8080/produtos/${produto.id}`)
      );
      const responses = await Promise.all(promises);
      return responses.every(response => response.data.status === 'Ativo');
    } catch (error) {
      console.error('Erro ao verificar status dos produtos:', error);
      return false;
    }
  };

  const finalizarCompra = async () => {
    const produtosAtivos = await verificarProdutosAtivos();
    if (!produtosAtivos) {
      alert('Um ou mais produtos no carrinho estão inativos. Não é possível finalizar a compra.');
      return;
    }

    axios.post('http://localhost:8080/pedidos/finalizar-compra', carrinho, {
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    })
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
    <>
      <Header searchHidden={true} navbarHidden={true} />
      <div className="carrinho-container">
        <h2 className="carrinho-title">Carrinho</h2>
        <ProdutosList 
          produtos={carrinho} 
          aumentarQuantidade={aumentarQuantidade} 
          diminuirQuantidade={diminuirQuantidade} 
          removerProduto={removerProduto}
        />
        <button className="finalizar-button" onClick={finalizarCompra}>Finalizar Compra</button>
      </div>
    </>
  );
};

export default Carrinho;