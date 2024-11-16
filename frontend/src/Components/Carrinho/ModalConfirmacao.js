import React from 'react';
import './ModalConfirmacao.css';

const ModalConfirmacao = ({ isOpen, onClose, onConfirm, message }) => {
  if (!isOpen) return null;

  return (
    <div className="carrinho-modal-overlay">
      <div className="carrinho-modal-content">
        <p>{message}</p>
        <div className="carrinho-modal-actions">
          <button onClick={onConfirm} className="carrinho-confirm-button">Confirmar</button>
          <button onClick={onClose} className="carrinho-cancel-button">Cancelar</button>
        </div>
      </div>
    </div>
  );
};

export default ModalConfirmacao; 