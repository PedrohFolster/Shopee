import React from 'react';
import PropTypes from 'prop-types';

const LojaInfo = ({ lojaInfo }) => {
  return (
    <div className="informacoes-loja">
      <p><strong>Nome da Loja:</strong> {lojaInfo?.nome || 'Informação não disponível'}</p>
      <p><strong>Quantidade de Produtos:</strong> {lojaInfo?.quantidadeProdutos || 0}</p>
      <p><strong>Produtos Vendidos:</strong> {lojaInfo?.quantidadeVendidos || 0}</p>
      <p><strong>Valor Total:</strong> R$ {lojaInfo?.valorTotal ? lojaInfo.valorTotal.toFixed(2) : '0.00'}</p>
    </div>
  );
};

LojaInfo.propTypes = {
  lojaInfo: PropTypes.object,
};

export default LojaInfo; 