import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../../Components/Menu/Items/Header/Header';
import ProdutoHome from '../../Components/Product/ProdutoHome';
import '../CSS/Home.css';

const Home = () => {
  const [produtos, setProdutos] = useState([]);
  const [categorias, setCategorias] = useState([]);
  const [filtros, setFiltros] = useState({
    precoMin: '',
    precoMax: '',
    categoria: ''
  });

  useEffect(() => {
    axios.get('http://localhost:8080/produtos/ativos')
      .then(response => {
        setProdutos(response.data);
      })
      .catch(error => {
        console.error('Erro ao buscar produtos ativos:', error);
      });

    axios.get('http://localhost:8080/categorias-p')
      .then(response => {
        setCategorias(response.data);
      })
      .catch(error => {
        console.error('Erro ao buscar categorias:', error);
      });
  }, []);

  const adicionarAoCarrinho = (produto) => {
    const carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
    carrinho.push(produto);
    localStorage.setItem('carrinho', JSON.stringify(carrinho));
    alert('Produto adicionado ao carrinho!');
  };

  const handleFiltroChange = (e) => {
    const { name, value } = e.target;
    setFiltros({ ...filtros, [name]: value });
  };

  const limparFiltros = () => {
    setFiltros({
      precoMin: '',
      precoMax: '',
      categoria: ''
    });
  };

  const produtosFiltrados = produtos.filter(produto => {
    const precoValido = (!filtros.precoMin || produto.preco >= parseFloat(filtros.precoMin)) &&
                        (!filtros.precoMax || produto.preco <= parseFloat(filtros.precoMax));
    

    const categoriaValida = !filtros.categoria || produto.categoriaProdutoId === parseInt(filtros.categoria, 10);
    return precoValido && categoriaValida;
  });

  return (
    <div className='home'>
      <Header />
      <main className='home-content'>
        <aside className='filtros'>
          <h3>Filtrar Produtos</h3>
          <div className='filtro-item'>
            <label>Preço Mínimo:</label>
            <input
              type='number'
              name='precoMin'
              value={filtros.precoMin}
              onChange={handleFiltroChange}
            />
          </div>
          <div className='filtro-item'>
            <label>Preço Máximo:</label>
            <input
              type='number'
              name='precoMax'
              value={filtros.precoMax}
              onChange={handleFiltroChange}
            />
          </div>
          <div className='filtro-item'>
            <label>Faixa de Preço:</label>
            <div className='range-slider'>
              <input
                type='range'
                min='0'
                max='10000'
                value={filtros.precoMin}
                onChange={(e) => setFiltros({ ...filtros, precoMin: e.target.value })}
              />
              <input
                type='range'
                min='0'
                max='10000'
                value={filtros.precoMax}
                onChange={(e) => setFiltros({ ...filtros, precoMax: e.target.value })}
              />
            </div>
            <div className='range-values'>
              <span>{filtros.precoMin}</span> - <span>{filtros.precoMax}</span>
            </div>
          </div>
          <div className='filtro-item'>
            <label>Categoria:</label>
            <select
              name='categoria'
              value={filtros.categoria}
              onChange={handleFiltroChange}
            >
              <option value=''>Todas</option>
              {categorias.map(categoria => (
                <option key={categoria.id} value={categoria.id}>
                  {categoria.nome}
                </option>
              ))}
            </select>
          </div>
          <button onClick={limparFiltros}>Limpar Filtros</button>
        </aside>
        <div className='produtos-list'>
          {produtosFiltrados.length > 0 ? (
            produtosFiltrados.map(produto => (
              <ProdutoHome 
                key={produto.id} 
                produto={produto} 
                onAddToCart={adicionarAoCarrinho} 
              />
            ))
          ) : (
            <p>Nenhum produto encontrado.</p>
          )}
        </div>
      </main>
    </div>
  );
};

export default Home;