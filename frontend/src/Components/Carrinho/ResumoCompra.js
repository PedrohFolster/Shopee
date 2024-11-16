import React from 'react';
import '../../Pages/CSS/Carrinho.css';

const ResumoCompra = ({ produtos, total, finalizarCompra }) => {
  return (
    <div className="resumo-compra">
      <h3>Resumo da Compra</h3>
      <ul className="resumo-lista">
        {produtos.map(produto => (
          <li key={produto.id} className="resumo-item">
            <span className="resumo-nome">{produto.nome}</span>
            <span className="resumo-quantidade">Qtd: {produto.quantidade}</span>
            <span className="resumo-total">R$ {(produto.preco * produto.quantidade).toFixed(2)}</span>
          </li>
        ))}
      </ul>
      <p>Total: R$ {total}</p>
      <button className="finalizar-button" onClick={finalizarCompra}>Finalizar Compra</button>
    </div>
  );
};

export default ResumoCompra;