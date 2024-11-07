import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../../Components/Menu/Items/Header/Header';
import ProdutoHome from '../../Components/Product/ProdutoHome';
import '../CSS/Home.css';

const Home = () => {
  const [produtos, setProdutos] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/produtos/ativos')
      .then(response => {
        setProdutos(response.data);
      })
      .catch(error => {
        console.error('Erro ao buscar produtos ativos:', error);
      });
  }, []);

  const adicionarAoCarrinho = (produto) => {
    const carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
    carrinho.push(produto);
    localStorage.setItem('carrinho', JSON.stringify(carrinho));
    alert('Produto adicionado ao carrinho!');
  };

  return (
    <div className='home'>
      <Header />
      <main className='home-content'>
        <div className='produtos-list'>
          {produtos.length > 0 ? (
            produtos.map(produto => (
              <ProdutoHome 
                key={produto.id} 
                produto={produto} 
                onAddToCart={adicionarAoCarrinho} 
              />
            ))
          ) : (
            <p>Nenhum produto cadastrado.</p>
          )}
        </div>
      </main>
    </div>
  );
};

export default Home;