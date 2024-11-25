import React, { useState } from 'react';
import PropTypes from 'prop-types';
import ProdutoVendido from './ProdutoVendido';
import ProdutoVendidoModal from './ProdutoVendidoModal';

const ProdutosVendidosList = ({ produtosVendidos, onStatusChange }) => {
    const [showModal, setShowModal] = useState(false);
    const [selectedProduto, setSelectedProduto] = useState(null);

    const handleEditClick = (produto) => {
        setSelectedProduto(produto);
        setShowModal(true);
    };

    return (
        <div className="produtos-container">
            {Array.isArray(produtosVendidos) && produtosVendidos.length > 0 ? (
                produtosVendidos.map(produto => (
                    <ProdutoVendido
                        key={produto.id}
                        produto={produto}
                        onEdit={() => handleEditClick(produto)}
                    />
                ))
            ) : (
                <p>Nenhum produto vendido encontrado</p>
            )}

            {showModal && selectedProduto && (
                <ProdutoVendidoModal
                    showModal={showModal}
                    setShowModal={setShowModal}
                    produto={selectedProduto}
                    setStatus={(status) => setSelectedProduto({ ...selectedProduto, status: status.nomeStatus })}
                    handleSubmit={(status) => {
                        onStatusChange(selectedProduto.pedidoId, selectedProduto.id, status.id);
                        setSelectedProduto({ ...selectedProduto, status: status.nomeStatus });
                        setShowModal(false);
                    }}
                />
            )}
        </div>
    );
};

ProdutosVendidosList.propTypes = {
    produtosVendidos: PropTypes.array.isRequired,
    onStatusChange: PropTypes.func.isRequired,
};

export default ProdutosVendidosList;