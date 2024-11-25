import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import ProdutoVendido from './ProdutoVendido';
import ProdutoVendidoModal from './ProdutoVendidoModal';
import axios from 'axios';

const ProdutosVendidosList = ({ produtosVendidos, onStatusChange }) => {
    const [showModal, setShowModal] = useState(false);
    const [selectedProduto, setSelectedProduto] = useState(null);
    const [imagens, setImagens] = useState({});

    useEffect(() => {
        const fetchImagens = async () => {
            const novasImagens = {};
            for (const produto of produtosVendidos) {
                if (produto.produtoId) {
                    try {
                        const response = await axios.get(`${process.env.REACT_APP_API_URL}/produtos/${produto.produtoId}/imagem`);
                        novasImagens[produto.produtoId] = response.data;
                    } catch (error) {
                        console.error('Erro ao buscar imagem:', error);
                    }
                } else {
                    console.warn(`produtoId não definido para o produto: ${produto.nomeItem}`);
                }
            }
            setImagens(novasImagens);
        };

        fetchImagens();
    }, [produtosVendidos]);

    const handleEditClick = (produto) => {
        setSelectedProduto(produto);
        setShowModal(true);
    };

    return (
        <div className="produtos-container-vendidos">
            {Array.isArray(produtosVendidos) && produtosVendidos.length > 0 ? (
                produtosVendidos.map(produto => (
                    <ProdutoVendido
                        key={produto.id}
                        produto={{
                            ...produto,
                            imagem: imagens[produto.produtoId],
                        }}
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
