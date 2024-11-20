import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import Header from '../../Components/Menu/Items/Header/Header';
import ProdutoSwiperItem from '../../Components/Product/ProdutoSwiperItem';
import '../CSS/Produto.css';
import { toast } from 'react-toastify';

const Produto = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [produto, setProduto] = useState(null);
  const [produtosRelacionados, setProdutosRelacionados] = useState([]);
  const swiperRef = useRef(null);
  const [produtosVisiveis, setProdutosVisiveis] = useState([]);
  const maxProdutosVisiveis = 6;
  const [indiceAtual, setIndiceAtual] = useState(0);
  const [nomeLoja, setNomeLoja] = useState('');

  useEffect(() => {
    const fetchProduto = async () => {
      try {
        const response = await axios.get(`${process.env.REACT_APP_API_URL}/produtos/${id}`);
        setProduto(response.data);
        fetchProdutosRelacionados(response.data.categoriaProdutoId, response.data.id);
        fetchNomeLoja(response.data.id);
      } catch (error) {
        console.error('Erro ao buscar produto:', error);
      }
    };

    const fetchProdutosRelacionados = async (categoriaId, produtoId) => {
      try {
        const response = await axios.get(`${process.env.REACT_APP_API_URL}/produtos/categoria/${categoriaId}`);
        const produtosFiltrados = response.data.filter(produto => produto.id !== produtoId);
        
        const produtosEmbaralhados = produtosFiltrados.sort(() => Math.random() - 0.5);
        
        setProdutosRelacionados(produtosEmbaralhados);
        setProdutosVisiveis(produtosEmbaralhados.slice(0, maxProdutosVisiveis));
      } catch (error) {
        console.error('Erro ao buscar produtos relacionados:', error);
      }
    };

    const fetchNomeLoja = async (produtoId) => {
      try {
        const response = await axios.get(`${process.env.REACT_APP_API_URL}/produtos/${produtoId}/loja`);
        setNomeLoja(response.data);
      } catch (error) {
        console.error('Erro ao buscar nome da loja:', error);
      }
    };

    fetchProduto();
  }, [id]);

  const adicionarAoCarrinho = (produto) => {
    const carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
    const produtoExistente = carrinho.find(item => item.id === produto.id);

    if (produtoExistente) {
      toast.info('Produto já está no carrinho!', {
        className: 'toast-clickable'
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

  const scrollLeft = () => {
    if (indiceAtual > 0) {
      const novoIndice = Math.max(indiceAtual - maxProdutosVisiveis, 0);
      setIndiceAtual(novoIndice);
      setProdutosVisiveis(produtosRelacionados.slice(novoIndice, novoIndice + maxProdutosVisiveis));
      
      if (swiperRef.current) {
        swiperRef.current.scrollTo({
          left: swiperRef.current.scrollLeft - swiperRef.current.clientWidth,
          behavior: 'smooth'
        });
      }
    }
  };

  const scrollRight = () => {
    if (indiceAtual + maxProdutosVisiveis < produtosRelacionados.length) {
      const novoIndice = Math.min(indiceAtual + maxProdutosVisiveis, produtosRelacionados.length - maxProdutosVisiveis);
      setIndiceAtual(novoIndice);
      setProdutosVisiveis(produtosRelacionados.slice(novoIndice, novoIndice + maxProdutosVisiveis));
      
      if (swiperRef.current) {
        swiperRef.current.scrollTo({
          left: swiperRef.current.scrollLeft + swiperRef.current.clientWidth,
          behavior: 'smooth'
        });
      }
    }
  };

  const handleLojaClick = () => {
    if (produto && produto.lojaId) {
      navigate(`/loja/${produto.lojaId}`);
    }
  };

  if (!produto) return <p>Carregando...</p>;

  return (
    <div className='produto-page'>
      <Header />
      <div className='produto-container'>
        <div className='produto-content'>
          <h1 className='produto-nome'>{produto.nome}</h1>
          <div className='produto-imagem'>
            <img src={produto.imagem} alt={produto.nome} />
          </div>
          <div className='produto-detalhes'>
            <p className='produto-preco'>R$ {produto.preco.toFixed(2)}</p>
            <div className='produto-descricao-container'>
              <p className='produto-loja'>
                Vendido por:     
                <span 
                  className='link-loja' 
                  onClick={handleLojaClick}
                  style={{ cursor: 'pointer' }}
                >
                  {" " +nomeLoja}
                </span>
              </p>
              <p className='produto-descricao'>{produto.descricao}</p>
            </div>
            <button className='botao-carrinho' onClick={() => adicionarAoCarrinho(produto)}>Adicionar ao Carrinho</button>
          </div>
        </div>
        <div className='produtos-relacionados'>
          <h2>Produtos Relacionados</h2>
          <div className='swiper-container'>
            <button className='swiper-button swiper-button-left' onClick={scrollLeft}>&lt;</button>
            <div className='swiper' ref={swiperRef}>
              {produtosVisiveis.map(produtoRelacionado => (
                <ProdutoSwiperItem 
                  key={produtoRelacionado.id} 
                  produto={produtoRelacionado} 
                />
              ))}
            </div>
            <button className='swiper-button swiper-button-right' onClick={scrollRight}>&gt;</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Produto;
