import React from 'react';
import PropTypes from 'prop-types';
import './ProdutoHome.css';

const ProdutoHome = ({ produto, onAddToCart }) => {
    const formatarPreco = (preco) => {
        return new Intl.NumberFormat('pt-BR', {
            style: 'currency',
            currency: 'BRL'
        }).format(preco);
    };

    return (
        <div className="produto-item home-style">
            <img src={produto.imagem} alt={produto.nome} />
            <h3>{produto.nome}</h3>
            <p className="preco">{formatarPreco(produto.preco)}</p>
            <button type="button" onClick={() => onAddToCart(produto)}>
                Adicionar ao Carrinho
            </button>
        </div>
    );
};

ProdutoHome.propTypes = {
    produto: PropTypes.object.isRequired,
    onAddToCart: PropTypes.func.isRequired,
};

export default ProdutoHome; 