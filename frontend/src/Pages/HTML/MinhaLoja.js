import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../CSS/MinhaLoja.css'; // Importando o arquivo CSS

const MinhaLoja = () => {
    const [lojaInfo, setLojaInfo] = useState(null);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        // Buscar informações da loja com ID 1
        axios.get('http://localhost:8080/lojas/1', { withCredentials: true })
            .then(lojaResponse => {
                setLojaInfo(lojaResponse.data);
            })
            .catch(lojaError => {
                console.error('Erro ao buscar informações da loja:', lojaError);
                setError('Erro ao carregar informações da loja.');
            });
    }, []);

    const handleCadastrarProduto = () => {
        navigate('/criar-produto');
      };

    if (error) {
        return <p style={{ color: 'red' }}>{error}</p>;
    }

    if (!lojaInfo) {
        return <p>Carregando informações da loja...</p>;
    }

    return (
        <div className="minha-loja-container">
            <h1 className="titulo">Bem-vindo à Minha Loja</h1>
            <div className="informacoes-loja">
                <h2>Informações da Loja</h2>
                <p><strong>Nome da Loja:</strong> {lojaInfo.nome || 'Informação não disponível'}</p>
                <p><strong>Quantidade de Produtos Cadastrados:</strong> {lojaInfo.quantidadeProdutos || 0}</p>
                <p><strong>Quantidade de Produtos Vendidos:</strong> {lojaInfo.quantidadeVendidos || 0}</p>
                <p><strong>Valor Total:</strong> R$ {lojaInfo.valorTotal ? lojaInfo.valorTotal.toFixed(2) : '0.00'}</p>
            </div>
            <button className="botao-cadastrar" onClick={handleCadastrarProduto}>Cadastrar Novo Produto</button>
        </div>
    );
};

export default MinhaLoja;