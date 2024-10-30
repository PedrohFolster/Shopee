import React, { useState, useEffect } from 'react';
import "../CSS/CriarProduto.css";
import Header from '../../Components/Menu/Items/Header/Header';
import axios from 'axios';

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
    axios.get('http://localhost:8080/categorias-p')
      .then(response => {
        setCategorias(response.data);
      })
      .catch(error => {
        console.error('Erro ao buscar categorias:', error);
      });

    axios.get('http://localhost:8080/status')
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
  
    axios.post('http://localhost:8080/produtos', produto, {
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
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <input type="text" placeholder="Nome" value={nome} onChange={(e) => setNome(e.target.value)} required />
          </div>
          <div className="form-group">
            <input type="number" placeholder="Preço" value={preco} onChange={(e) => setPreco(e.target.value)} required />
          </div>
          <div className="form-group">
            <input type="text" placeholder="Imagem" value={imagem} onChange={(e) => setImagem(e.target.value)} />
          </div>
          <div className="form-group">
            <input type="number" placeholder="Estoque" value={estoque} onChange={(e) => setEstoque(e.target.value)} required />
          </div>
          <div className="form-group">
            <select value={categoriaProdutoId} onChange={(e) => setCategoriaProdutoId(e.target.value)} required>
              <option value="">Selecione a Categoria</option>
              {categorias.map(categoria => (
                <option key={categoria.id} value={categoria.id}>
                  {categoria.nome}
                </option>
              ))}
            </select>
          </div>
          <div className="form-group">
            <select value={statusId} onChange={(e) => setStatusId(e.target.value)} required>
              <option value="">Selecione o Status</option>
              {statusList.map(status => (
                <option key={status.id} value={status.id}>
                  {status.nomeStatus} {/* Use "nomeStatus" aqui */}
                </option>
              ))}
            </select>
          </div>
          <div className="form-group-full">
            <textarea placeholder="Descrição" value={descricao} onChange={(e) => setDescricao(e.target.value)} required />
          </div>
          <button type="submit">Salvar Produto</button>
        </form>
        {mensagem && <p>{mensagem}</p>}
      </div>
    </div>
  );
};

export default CriarProduto;