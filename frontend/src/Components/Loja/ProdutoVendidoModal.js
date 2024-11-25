import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import Button from '../../Components/Button/Button';

const ProdutoVendidoModal = ({ showModal, setShowModal, produto, setStatus, handleSubmit }) => {
    const [statusList, setStatusList] = useState([]);

    useEffect(() => {
        if (showModal) {
            axios.get(`${process.env.REACT_APP_API_URL}/status-pedidos`)
                .then(response => {
                    setStatusList(response.data);
                })
                .catch(error => {
                    console.error('Erro ao carregar status:', error);
                });
        }
    }, [showModal]);

    if (!showModal) return null;

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <button className="modal-close" onClick={() => setShowModal(false)}>×</button>
                <div className="form-container">
                    <h2>Detalhes do Produto Vendido</h2>
                    <div className="separator"></div>
                    <form onSubmit={handleSubmit}>
                        <div className="form-group">
                            <p><strong>Nome:</strong> {produto.nomeItem}</p>
                        </div>
                        <div className="form-group">
                            <p><strong>Valor:</strong> {produto.valor}</p>
                        </div>
                        <div className="form-group">
                            <p><strong>Quantidade:</strong> {produto.quantidade}</p>
                        </div>
                        <div className="form-group">
                            <p><strong>Valor Total:</strong> {produto.valorTotal}</p>
                        </div>
                        <div className="form-group-center-status">
                            <p><strong>Status:</strong></p>
                            <select value={produto.status} onChange={(e) => setStatus(e.target.value)} required>
                                <option value="">Selecione o Status</option>
                                {statusList.map(status => (
                                    <option key={status.id} value={status.nomeStatus}>
                                        {status.nomeStatus}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <Button type="button-register" onClick={handleSubmit}>
                            Salvar
                        </Button>
                    </form>
                </div>
            </div>
        </div>
    );
};

ProdutoVendidoModal.propTypes = {
    showModal: PropTypes.bool.isRequired,
    setShowModal: PropTypes.func.isRequired,
    produto: PropTypes.object.isRequired,
    setStatus: PropTypes.func.isRequired,
    handleSubmit: PropTypes.func.isRequired,
};

export default ProdutoVendidoModal;