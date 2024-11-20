import React, { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import Header from '../../Components/Menu/Items/Header/Header';
import LojaInfo from '../../Components/Loja/LojaInfo';
import ProdutosList from '../../Components/Loja/ProdutosList';
import ProdutoModal from '../../Components/Loja/ProdutoModal';
import '../CSS/MinhaLoja.css';
import '../CSS/CriarProduto.css';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../../Util/Authentication';
import api from '../../Util/ApiConfig';
const MinhaLoja = () => {
    const { isAuthenticated } = useContext(AuthContext);
    const [lojaInfo, setLojaInfo] = useState(null);
    const [error, setError] = useState(null);
    const [activeTab, setActiveTab] = useState('dados');
    const [showModal, setShowModal] = useState(false);
    
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
    const [produtos, setProdutos] = useState([]);
    const [editingProduto, setEditingProduto] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        if (!isAuthenticated) {
            navigate('/login');
            return;
        }

        api.get(`${process.env.REACT_APP_API_URL}/lojas/verificar-loja`, { withCredentials: true })
            .then(response => {
                if (response.data !== "Redirecionar para /minha-loja") {
                    navigate("/CreateLoja");
                }
            })
            .catch(error => {
                console.error('Erro ao verificar loja:', error);
            });
    }, [isAuthenticated, navigate]);

  useEffect(() => {
    api.get(`${process.env.REACT_APP_API_URL}/lojas/minha-loja`, { withCredentials: true })
      .then(lojaResponse => {
        setLojaInfo(lojaResponse.data);
      })
      .catch(lojaError => {
        if (lojaError.response && lojaError.response.status === 401) {
          console.error('Usuário não autenticado. Redirecionando para login.');
        } else {
          console.error('Erro ao buscar informações da loja:', lojaError);
          setError('Erro ao carregar informações da loja.');
        }
      });
  }, []);

  useEffect(() => {
    if (showModal) {
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
    }
  }, [showModal]);

  useEffect(() => {
    if (activeTab === 'produtos' && lojaInfo?.id) {
      axios.get(`${process.env.REACT_APP_API_URL}/produtos/loja/${lojaInfo.id}`, { withCredentials: true })
        .then(response => {
          setProdutos(response.data);
        })
        .catch(error => {
          if (error.response && error.response.status === 401) {
            console.error('Usuário não autenticado. Redirecionando para login.');
          } else {
            console.error('Erro ao buscar produtos:', error);
            setError('Erro ao carregar produtos da loja.');
          }
        });
    }
  }, [activeTab, lojaInfo]);

  const handleEditarProduto = (produto) => {
    setEditingProduto(produto);
    setNome(produto.nome);
    setDescricao(produto.descricao);
    setPreco(produto.preco.toString());
    setImagem(produto.imagem);
    setEstoque(produto.estoque.toString());
    setCategoriaProdutoId(produto.categoriaProdutoId.toString());
    setStatusId(produto.statusId.toString());
    setShowModal(true);
  };

  const validarCampos = () => {
    if (!isValidNomeProduto(nome)) {
      setMensagem('O nome do produto está inválido. Deve ter entre 1 e 100 caracteres.');
      return false;
    }
    if (!isValidPreco(preco)) {
      setMensagem('O preço está inválido. Deve ser um número entre 0 e 99999.');
      return false;
    }
    if (!isValidImagem(imagem)) {
      setMensagem('O campo imagem está inválido.');
      return false;
    }
    if (!isValidEstoque(estoque)) {
      setMensagem('O campo estoque está inválido. Deve ser um número entre 0 e 99999.');
      return false;
    }
    if (!isValidCategoriaProduto(categoriaProdutoId)) {
      setMensagem('A categoria é obrigatória.');
      return false;
    }
    if (!isValidStatus(statusId)) {
      setMensagem('O status é obrigatório.');
      return false;
    }
    if (!isValidDescricao(descricao)) {
      setMensagem('O campo descrição está inválido. Deve ter entre 5 e 500 caracteres.');
      return false;
    }
    return true;
  };


  const isValidNomeProduto = (nome) => {
    return nome && nome.trim().length > 0 && nome.length <= 100;
  };

  const isValidPreco = (preco) => {
    const precoNum = parseFloat(preco);
    return !isNaN(precoNum) && precoNum >= 0 && precoNum < 99999;
  };

  const isValidImagem = (imagem) => {
    return imagem && imagem.trim().length > 0;
  };

  const isValidEstoque = (estoque) => {
    const estoqueNum = parseInt(estoque, 10);
    return !isNaN(estoqueNum) && estoqueNum >= 0 && estoqueNum <= 99999;
  };

  const isValidCategoriaProduto = (categoriaProdutoId) => {
    return categoriaProdutoId && categoriaProdutoId.trim().length > 0;
  };

  const isValidStatus = (statusId) => {
    return statusId && (statusId === '1' || statusId === '2');
  };

  const isValidDescricao = (descricao) => {
    return descricao && descricao.trim().length >= 5 && descricao.length <= 500;
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!validarCampos()) {
      return;
    }

    const produto = {
      nome,
      descricao,
      preco: parseFloat(preco),
      imagem,
      estoque: parseInt(estoque, 10),
      categoriaProdutoId: parseInt(categoriaProdutoId, 10),
      statusId: parseInt(statusId, 10),
    };

    const url = editingProduto 
      ? `${process.env.REACT_APP_API_URL}/produtos/${editingProduto.id}`
      : `${process.env.REACT_APP_API_URL}/produtos`;
    
    const method = editingProduto ? 'put' : 'post';

    axios[method](url, produto, {
      headers: {
        'Content-Type': 'application/json',
      },
      withCredentials: true
    })
    .then(response => {
      setMensagem(editingProduto ? 'Produto atualizado com sucesso!' : 'Produto criado com sucesso!');
      setNome('');
      setDescricao('');
      setPreco('');
      setImagem('');
      setEstoque('');
      setCategoriaProdutoId('');
      setStatusId('');
      setEditingProduto(null);
      
      if (lojaInfo?.id) {
        axios.get(`${process.env.REACT_APP_API_URL}/produtos/loja/${lojaInfo.id}`, { withCredentials: true })
          .then(response => {
            setProdutos(response.data);
          })
          .catch(error => {
            console.error('Erro ao buscar produtos:', error);
          });
      }
      
      setTimeout(() => {
        setShowModal(false);
        setMensagem('');
      }, 2000);
    })
    .catch(error => {
      console.error('Erro ao salvar produto:', error);
      setMensagem('Erro ao salvar produto.');
    });
  };

  const renderContent = () => {
    switch(activeTab) {
      case 'dados':
        return <LojaInfo lojaInfo={lojaInfo} />;
      case 'produtos':
        return (
          <ProdutosList
            produtos={produtos}
            onEdit={handleEditarProduto}
            onAddNew={() => setShowModal(true)}
            searchTerm={searchTerm}
            setSearchTerm={setSearchTerm}
          />
        );
      default:
        return null;
    }
  };

  return (
    <div className="minha-loja">
      <Header searchHidden={true} navbarHidden={true}/>
      <div className="minha-loja-container">
        <nav className="sidebar">
          <ul>
            <li 
              className={activeTab === 'dados' ? 'active' : ''} 
              onClick={() => setActiveTab('dados')}
            >
              Dados da Loja
            </li>
            <li 
              className={activeTab === 'produtos' ? 'active' : ''} 
              onClick={() => setActiveTab('produtos')}
            >
              Meus Produtos
            </li>
          </ul>
        </nav>
        <main className="minha-loja-content">
          <h2>MINHA LOJA</h2>
          <div className="separator"></div>
          {error && <div className="error-message">{error}</div>}
          {!lojaInfo ? (
            <p>Carregando informações da loja...</p>
          ) : (
            renderContent()
          )}
        </main>
        <ProdutoModal
          showModal={showModal}
          setShowModal={setShowModal}
          editingProduto={editingProduto}
          setEditingProduto={setEditingProduto}
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
          mensagem={mensagem}
        />
      </div>
    </div>
  );
};

export default MinhaLoja;