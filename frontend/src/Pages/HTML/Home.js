import React, { useState, useEffect} from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Header from '../../Components/Menu/Items/Header/Header';
import FiltroProdutos from '../../Components/Product/FiltroProdutos';
import ListaProdutos from '../../Components/Product/ListaProdutos';
import '../CSS/Home.css';
import { toast } from 'react-toastify';

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
    const fetchProdutos = async () => {
      try {
        const response = await axios.get('http://localhost:8080/produtos/ativos');
        setProdutos(response.data);
      } catch (error) {
        console.error('Erro ao buscar produtos ativos:', error);
      }
    };

    const fetchCategorias = async () => {
      try {
        const response = await axios.get('http://localhost:8080/categorias-p');
        setCategorias(response.data);
      } catch (error) {
        console.error('Erro ao buscar categorias:', error);
      }
    };

    fetchProdutos();
    fetchCategorias();
  }, []);

  const adicionarAoCarrinho = (produto) => {
    const carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
    const produtoExistente = carrinho.find(item => item.id === produto.id);

    if (produtoExistente) {
      toast.info('Produto já está no carrinho!', {
        className: 'toast-clickable',
        onClick: () => navigate('/carrinho'),
      });
    } else {
      carrinho.push(produto);
      localStorage.setItem('carrinho', JSON.stringify(carrinho));
      toast.success('Produto adicionado ao carrinho!', {
        onClick: () => navigate('/carrinho'),
        className: 'toast-clickable'
      });
    }
  };

  const handleFiltroChange = (e) => {
    const { name, value } = e.target;
    setFiltros({ ...filtros, [name]: value });
  };

  const limparFiltros = () => {
    setFiltros({
      preco: [0, 10000],
      categoria: '',
      nome: ''
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
        <FiltroProdutos 
          filtros={filtros} 
          setFiltros={setFiltros} 
          categorias={categorias} 
          handleFiltroChange={handleFiltroChange} 
          limparFiltros={limparFiltros} 
        />
        <ListaProdutos 
          produtosFiltrados={produtosFiltrados} 
          adicionarAoCarrinho={adicionarAoCarrinho} 
          handleProdutoClick={handleProdutoClick} 
        />
      </main>
    </div>
  );
};

export default Home;