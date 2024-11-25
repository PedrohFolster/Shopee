import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import Button from '../../Components/Button/Button';

const ProdutoVendidoModal = ({ showModal, setShowModal, produto, setStatus, handleSubmit }) => {
    const [statusList, setStatusList] = useState([]);
    const [selectedStatusId, setSelectedStatusId] = useState(null);

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

    const handleStatusSubmit = (e) => {
        e.preventDefault();
        const selectedStatus = statusList.find(status => status.id === selectedStatusId);
        if (!selectedStatus) {
            console.error('Status não encontrado');
            return;
        }
        handleSubmit(selectedStatus); // Passa o objeto status completo
    };

    if (!showModal) return null;

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <button className="modal-close" onClick={() => setShowModal(false)}>×</button>
                <div className="form-container">
                    <h2>Detalhes do Produto Vendido</h2>
                    <div className="separator"></div>
                    <form onSubmit={handleStatusSubmit}>
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
                            <select 
                                value={selectedStatusId || ''} 
                                onChange={(e) => setSelectedStatusId(Number(e.target.value))} 
                                required
                            >
                                <option value="">Selecione o Status</option>
                                {statusList.map(status => (
                                    <option key={status.id} value={status.id}>
                                        {status.nomeStatus}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <Button type="submit">
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