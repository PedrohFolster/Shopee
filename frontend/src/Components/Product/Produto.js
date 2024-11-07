import React from 'react';
import PropTypes from 'prop-types';
import './Produto.css'; // Arquivo CSS específico para o componente Produto

const Produto = ({ produto, onEdit, pageStyle }) => {
    const formatarPreco = (preco) => {
        return new Intl.NumberFormat('pt-BR', {
            style: 'currency',
            currency: 'BRL'
        }).format(preco);
    };

    const obterStatus = (statusId) => {
        return statusId === 1 ? 'Ativo' : 'Inativo';
    };

    return (
        <div className={`produto-item ${pageStyle}`}>
            <img src={produto.imagem} alt={produto.nome} />
            <h3>{produto.nome}</h3>
            <p className="preco">{formatarPreco(produto.preco)}</p>
            <div className="produto-info">
                <p className="estoque">Estoque: {produto.estoque}</p>
                <p className="status">Status: {obterStatus(produto.statusId)}</p>
            </div>
            <div className="produto-acoes">
                <button type="button" onClick={() => onEdit(produto)}>
                    Editar
                </button>
            </div>
        </div>
    );
};

Produto.propTypes = {
    produto: PropTypes.object.isRequired,
    onEdit: PropTypes.func.isRequired,
    pageStyle: PropTypes.string
};

export default Produto;