import React, { useState } from 'react';
import axios from 'axios';
import ProdutosList from '../../Components/Carrinho/ProdutosList';
import Header from '../../Components/Menu/Items/Header/Header';
import ResumoCompra from '../../Components/Carrinho/ResumoCompra';
import '../CSS/Carrinho.css';
import { toast } from 'react-toastify';

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

      const produtosAtivos = responses.every(response => {
        if (response.data.statusId !== 1) {
          removerProduto(response.data.id);
          toast.warn(`O produto ${response.data.nome} está inativo e foi removido do carrinho.`);
          return false;
        }
        return true;
      });

      return produtosAtivos;
    } catch (error) {
      console.error('Erro ao verificar status dos produtos:', error);
      return false;
    }
  };

  const finalizarCompra = async () => {
    if (carrinho.length === 0) {
      toast.warn('O carrinho está vazio. Adicione produtos antes de finalizar a compra.');
      return;
    }

    const produtosAtivos = await verificarProdutosAtivos();
    if (!produtosAtivos) {
      toast.error('Um ou mais produtos no carrinho estavam inativos e foram removidos.');
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

  const calcularTotal = () => {
    return carrinho.reduce((total, produto) => total + produto.preco * produto.quantidade, 0).toFixed(2);
  };

  return (
    <>
      <Header searchHidden={true} navbarHidden={true} />
      <div className="carrinho-container carrinho-page">
        <h2 className="carrinho-title">Carrinho</h2>
        <div className="carrinho-content">
          <ProdutosList 
            produtos={carrinho} 
            aumentarQuantidade={aumentarQuantidade} 
            diminuirQuantidade={diminuirQuantidade} 
            removerProduto={removerProduto}
          />
          <ResumoCompra produtos={carrinho} total={calcularTotal()} finalizarCompra={finalizarCompra} />
        </div>
      </div>
    </>
  );
};

export default Carrinho;