import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Pedido from '../../Components/Pedido/Pedido';
import Header from '../../Components/Menu/Items/Header/Header';
import '../CSS/PedidosPage.css';
import { toast } from 'react-toastify';

const PedidosPage = () => {
  const [pedidos, setPedidos] = useState([]);

  useEffect(() => {
    const fetchPedidos = async () => {
      try {
        const response = await axios.get(`${process.env.REACT_APP_API_URL}/pedidos`, {
          headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
          }
        });
        if (response.data.length === 0) {
          toast.info('Nenhum pedido encontrado para esta conta.');
        }
        setPedidos(response.data);
      } catch (error) {
        console.error('Erro ao buscar pedidos:', error);
      }
    };

    fetchPedidos();
  }, []);

  return (
    <>
      <Header searchHidden={true} navbarHidden={true} />
      <div className="pedidos-page">
        <h2>Meus Pedidos</h2>
        <div className="pedidos-list">
          {pedidos.map(pedido => (
            <Pedido key={pedido.idPedido} pedido={pedido} />
          ))}
        </div>
      </div>
    </>
  );
};

export default PedidosPage;