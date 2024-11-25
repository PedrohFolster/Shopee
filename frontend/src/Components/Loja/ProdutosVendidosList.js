import React, { useState } from 'react';
import PropTypes from 'prop-types';
import ProdutoLoja from '../../Components/Product/ProdutoLoja';
import ProdutoVendidoModal from '../../Components/Loja/ProdutoVendidoModal';

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
                produtosVendidos.map(pedido => (
                    <ProdutoLoja
                        key={pedido.id}
                        produto={{
                            nome: pedido.nomeItem,
                            valor: pedido.valor,
                            quantidade: pedido.quantidade,
                            valorTotal: pedido.valorTotal,
                            status: pedido.status
                        }}
                        onEdit={() => handleEditClick(pedido)}
                        pageStyle="minha-loja-style"
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
                    setStatus={(status) => setSelectedProduto({ ...selectedProduto, status })}
                    handleSubmit={() => {
                        onStatusChange(selectedProduto.id, selectedProduto.status);
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