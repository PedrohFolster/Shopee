import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Pedido.css';

const Pedido = ({ pedido }) => {
  const [expandido, setExpandido] = useState(false);
  const [imagens, setImagens] = useState({});

  const toggleExpandido = () => {
    setExpandido(!expandido);
  };

  useEffect(() => {
    const fetchImagens = async () => {
      const novasImagens = {};
      for (const item of pedido.pedidoItens) {
        try {
          const response = await axios.get(`${process.env.REACT_APP_API_URL}/produtos/${item.produtoId}/imagem`);
          novasImagens[item.id] = response.data;
        } catch (error) {
          console.error('Erro ao buscar imagem:', error);
        }
      }
      setImagens(novasImagens);
    };

    fetchImagens();
  }, [pedido.pedidoItens]);

  return (
    <div className={`pedido-container ${expandido ? 'expandido' : ''}`} onClick={toggleExpandido}>
      <div className="pedido-info">
        <p>Pedido  #{pedido.idPedido}</p>
        <p>Valor Total: R$ {pedido.valorTotal.toFixed(2)}</p>
        <p>Data do Pedido: {pedido.dataPedido}</p>
      </div>
      {expandido && (
        <div className="pedido-detalhes">
          {pedido.pedidoItens.map(item => (
            <div key={item.id} className="pedido-item">
              <div className="pedido-item-info">
                <p>Item: {item.nomeItem}</p>
                <p>Quantidade: {item.quantidade}</p>
                <p>Valor: R$ {item.valor.toFixed(2)}</p>
                <p>Valor Total: R$ {(item.valor * item.quantidade).toFixed(2)}</p>
                <hr className="pedido-item-divisoria" />
              </div>
              {imagens[item.id] && <img src={imagens[item.id]} alt={item.nomeItem} className="produto-imagem" />}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Pedido;