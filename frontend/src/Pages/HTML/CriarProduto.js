import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../../Components/Menu/Items/Header/Header';
import FormularioProduto from '../../Components/CriarProduto/FormularioProduto';
import Mensagem from '../../Components/CriarProduto/Mensagem';
import '../CSS/CriarProduto.css';

const CriarProduto = () => {
  const [nome, setNome] = useState('');
  const [descricao, setDescricao] = useState('');
  const [preco, setPreco] = useState('');
  const [imagem, setImagem] = useState('');
  const [estoque, setEstoque] = useState('');
  const [categoriaProdutoId, setCategoriaProdutoId] = useState('');
  const [statusId, setStatusId] = useState('');
  const [mensagem, setMensagem] = useState('');
  const [categorias, setCategorias] = useState([]);
  const [statusList, setStatusList] = useState([]);

  useEffect(() => {
    axios.get(`${process.env.REACT_APP_API_URL}/categorias-p`)
      .then(response => {
        setCategorias(response.data);
      })
      .catch(error => {
        console.error('Erro ao buscar categorias:', error);
      });

    axios.get(`${process.env.REACT_APP_API_URL}/status`)
      .then(response => {
        setStatusList(response.data);
      })
      .catch(error => {
        console.error('Erro ao buscar status:', error);
      });
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
  
    const produto = {
      nome,
      descricao,
      preco: parseFloat(preco),
      imagem,
      estoque: parseInt(estoque, 10),
      categoriaProdutoId: parseInt(categoriaProdutoId, 10),
      statusId: parseInt(statusId, 10),
    };
  
    axios.post(`${process.env.REACT_APP_API_URL}/produtos`, produto, {
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then(response => {
      console.log('Produto criado com sucesso:', response.data);
      setMensagem('Produto criado com sucesso!');
    })
    .catch(error => {
      console.error('Erro ao criar produto:', error);
      setMensagem('Erro ao criar produto.');
    });
  };

  return (
    <div className='home'>
      <Header />
      <div className="form-container">
        <h1>Criar Produto</h1>
        <FormularioProduto
          nome={nome}
          setNome={setNome}
          descricao={descricao}
          setDescricao={setDescricao}
          preco={preco}
          setPreco={setPreco}
          imagem={imagem}
          setImagem={setImagem}
          estoque={estoque}
          setEstoque={setEstoque}
          categoriaProdutoId={categoriaProdutoId}
          setCategoriaProdutoId={setCategoriaProdutoId}
          statusId={statusId}
          setStatusId={setStatusId}
          categorias={categorias}
          statusList={statusList}
          handleSubmit={handleSubmit}
        />
        {mensagem && <Mensagem texto={mensagem} />}
      </div>
    </div>
  );
};

export default CriarProduto;