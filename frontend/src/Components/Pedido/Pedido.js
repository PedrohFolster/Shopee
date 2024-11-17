import React, { useState } from 'react';
import './Pedido.css';

const Pedido = ({ pedido }) => {
  const [expandido, setExpandido] = useState(false);

  const toggleExpandido = () => {
    setExpandido(!expandido);
  };

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
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Pedido;