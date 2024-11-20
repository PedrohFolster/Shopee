import React, { useEffect, useState } from 'react';
import axios from 'axios';

const LojaInfo = () => {
  const [lojaInfo, setLojaInfo] = useState(null);

  useEffect(() => {
    axios.get(`${process.env.REACT_APP_API_URL}/lojas/minha-loja/info`, { withCredentials: true })
      .then(response => {
        setLojaInfo(response.data);
      })
      .catch(error => {
        console.error('Erro ao buscar informações da loja:', error);
      });
  }, []);

  const formatCurrency = (value) => {
    return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value);
  };

  return (
    <div className="informacoes-loja">
      <p><strong>Nome da Loja:</strong> {lojaInfo?.nome || 'Informação não disponível'}</p>
      <p><strong>Quantidade de Produtos Diferentes:</strong> {lojaInfo?.quantidadeProdutosDiferentes || 0}</p>
      <p><strong>Produtos Totais:</strong> {lojaInfo?.produtosTotais || 0}</p>
      <p><strong>Valor Total do Estoque:</strong> {lojaInfo?.valorTotalEstoque ? formatCurrency(lojaInfo.valorTotalEstoque) : formatCurrency(0)}</p>
    </div>
  );
};

export default LojaInfo; 