import React from 'react';
import PropTypes from 'prop-types';
import './ProdutoVendido.css';

const ProdutoVendido = ({ produto, onEdit }) => {
    return (
        <div className="produto-card">
            <div className="produto-imagem">
                {produto.imagem ? (
                    <img src={produto.imagem} alt={produto.nomeItem} />
                ) : (
                    <p>Imagem não disponível</p>
                )}
            </div>
            <div className="produto-detalhes">
                <h3>{produto.nomeItem}</h3>
                <p>R$ {produto.valor.toFixed(2)}</p>
                <p>Quantidade: {produto.quantidade}</p>
                <p>Valor Total: R$ {produto.valorTotal.toFixed(2)}</p>
                <p>Status: {produto.status}</p>
                <button onClick={onEdit}>Editar</button>
            </div>
        </div>
    );
};

ProdutoVendido.propTypes = {
    produto: PropTypes.object.isRequired,
    onEdit: PropTypes.func.isRequired,
};

export default ProdutoVendido;