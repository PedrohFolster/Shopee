import React from 'react';
import PropTypes from 'prop-types';
import { useNavigate } from 'react-router-dom';
import './ProdutoSwiperItem.css';

const ProdutoSwiperItem = ({ produto }) => {
    const navigate = useNavigate();

    const handleProdutoClick = () => {
        navigate(`/produto/${produto.id}`);
    };

    return (
        <div className="produto-swiper-item" onClick={handleProdutoClick}>
            <img src={produto.imagem} alt={produto.nome} />
            <p className="preco">{new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(produto.preco)}</p>
        </div>
    );
};

ProdutoSwiperItem.propTypes = {
    produto: PropTypes.object.isRequired,
};

export default ProdutoSwiperItem; 