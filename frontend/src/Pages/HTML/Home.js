import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Range } from 'react-range';
import { useNavigate } from 'react-router-dom';
import Header from '../../Components/Menu/Items/Header/Header';
import ProdutoHome from '../../Components/Product/ProdutoHome';
import '../CSS/Home.css';

const Home = () => {
  const [produtos, setProdutos] = useState([]);
  const [categorias, setCategorias] = useState([]);
  const [filtros, setFiltros] = useState({
    preco: [0, 10000],
    categoria: '',
    nome: ''
  });

  const navigate = useNavigate();

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
      preco: [0, 10000],
      categoria: ''
    });
  };

  const produtosFiltrados = produtos.filter(produto => {
    const precoValido = produto.preco >= filtros.preco[0] && produto.preco <= filtros.preco[1];
    const categoriaValida = !filtros.categoria || produto.categoriaProdutoId === parseInt(filtros.categoria, 10);
    const nomeValido = produto.nome.toLowerCase().includes(filtros.nome.toLowerCase());
    return precoValido && categoriaValida && nomeValido;
  });

  const handleProdutoClick = (id) => {
    navigate(`/produto/${id}`);
  };

  return (
    <div className='home'>
      <Header />
      <main className='home-content'>
        <aside className='filtros'>
          <h3>Filtrar Produtos</h3>
          <div className='filtro-item'>
            <label>Nome do Produto:</label>
            <input
              type='text'
              name='nome'
              value={filtros.nome}
              onChange={handleFiltroChange}
              placeholder='Buscar por nome'
            />
          </div>
          <div className='filtro-item'>
            <label>Faixa de Preço:</label>
            <Range
              step={100}
              min={0}
              max={10000}
              values={filtros.preco}
              onChange={(values) => setFiltros({ ...filtros, preco: values })}
              renderTrack={({ props, children }) => (
                <div key={props.key} {...props} style={{ ...props.style, height: '6px', background: '#ddd' }}>
                  {children}
                </div>
              )}
              renderThumb={({ props }) => (
                <div key={props.key} {...props} style={{ ...props.style, height: '20px', width: '20px', background: '#999', borderRadius: '50%' }} />
              )}
            />
            <div className='preco-inputs'>
              <div className='preco-input'>
                <label>Mínimo</label>
                <input
                  type='number'
                  name='precoMin'
                  value={filtros.preco[0]}
                  onChange={e => setFiltros({ ...filtros, preco: [parseFloat(e.target.value), filtros.preco[1]] })}
                />
              </div>
              <div className='preco-input'>
                <label>Máximo</label>
                <input
                  type='number'
                  name='precoMax'
                  value={filtros.preco[1]}
                  onChange={e => setFiltros({ ...filtros, preco: [filtros.preco[0], parseFloat(e.target.value)] })}
                />
              </div>
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
                onNameClick={() => handleProdutoClick(produto.id)}
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