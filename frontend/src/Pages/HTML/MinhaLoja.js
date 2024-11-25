import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../../Components/Menu/Items/Header/Header';
import LojaInfo from '../../Components/Loja/LojaInfo';
import ProdutosList from '../../Components/Loja/ProdutosList';
import ProdutoModal from '../../Components/Loja/ProdutoModal';
import ProdutosVendidosList from '../../Components/Loja/ProdutosVendidosList';
import '../CSS/MinhaLoja.css';
import '../CSS/CriarProduto.css';
import { useNavigate } from 'react-router-dom';
import api from '../../Util/ApiConfig';
import { isTokenValid } from '../../Util/Authentication';
import { toast } from 'react-toastify';

const MinhaLoja = () => {
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
    const [mensagem] = useState('');
    const [statusList, setStatusList] = useState([]);
    const [produtos, setProdutos] = useState([]);
    const [produtosVendidos, setProdutosVendidos] = useState([]);
    const [editingProduto, setEditingProduto] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');
    const navigate = useNavigate();
    const [categorias, setCategorias] = useState([]);

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!isTokenValid(token)) {
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

        axios.get(`${process.env.REACT_APP_API_URL}/categorias-p`, { withCredentials: true })
            .then(response => {
                setCategorias(response.data);
            })
            .catch(error => {
                console.error('Erro ao carregar categorias:', error);
            });

        axios.get(`${process.env.REACT_APP_API_URL}/status`, { withCredentials: true })
            .then(response => {
                setStatusList(response.data);
            })
            .catch(error => {
                console.error('Erro ao carregar status:', error);
            });
    }, [navigate]);

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

    useEffect(() => {
      if (activeTab === 'vendidos' && lojaInfo?.id) {
          axios.get(`${process.env.REACT_APP_API_URL}/pedidos/itens-pedidos/${lojaInfo.id}`, { withCredentials: true })
              .then(response => {
                  const produtos = response.data.map(item => ({
                      id: item.id,
                      nomeItem: item.nomeItem,
                      valor: item.valor,
                      quantidade: item.quantidade,
                      valorTotal: item.valorTotal,
                      status: item.status,
                      pedidoId: item.idPedido
                  }));
                  setProdutosVendidos(produtos);
              })
              .catch(error => {
                  console.error('Erro ao buscar produtos vendidos:', error);
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
        setCategoriaProdutoId(produto.categoriaProdutoId);
        setStatusId(produto.statusId);
        setShowModal(true);
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
            case 'vendidos':
                return (
                    <ProdutosVendidosList
                        produtosVendidos={produtosVendidos}
                        onStatusChange={(pedidoId, itemId, novoStatusId) => handleStatusChange(pedidoId, itemId, novoStatusId)}
                    />
                );
            default:
                return null;
        }
    };

const handleStatusChange = (pedidoId, itemId, novoStatusId) => {
    axios.put(`${process.env.REACT_APP_API_URL}/pedidos/${pedidoId}/itens/${itemId}/status`, { statusId: novoStatusId }, { withCredentials: true })
        .then(response => {
            setProdutosVendidos(prevState => prevState.map(item => 
                item.id === itemId ? { ...item, status: novoStatusId } : item
            ));
        })
        .catch(error => {
            console.error('Erro ao atualizar status do item do pedido:', error);
        });
};

    const isValidNomeProduto = (nome) => {
        return nome && nome.trim().length > 0 && nome.length <= 100;
    };

    const isValidPreco = (preco) => {
        return !isNaN(preco) && parseFloat(preco) >= 0 && parseFloat(preco) < 99999;
    };

    const isValidImagem = (imagem) => {
        return imagem && imagem.trim().length > 0;
    };

    const isValidEstoque = (estoque) => {
        return !isNaN(estoque) && parseInt(estoque, 10) >= 0 && parseInt(estoque, 10) <= 99999;
    };

    const isValidCategoriaProduto = (categoriaProdutoId) => {
        return categoriaProdutoId !== '';
    };

    const isValidStatus = (statusId) => {
        return statusId !== '' && (statusId === '1' || statusId === '2');
    };

    const isValidDescricao = (descricao) => {
        return descricao && descricao.trim().length >= 5 && descricao.length <= 500;
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        let isValid = true;

        if (!isValidNomeProduto(nome)) {
            toast.error("Nome do produto é inválido.");
            isValid = false;
        }
        if (!isValidDescricao(descricao)) {
            toast.error("Descrição do produto é inválida.");
            isValid = false;
        }
        if (!isValidPreco(preco)) {
            toast.error("Preço do produto é inválido.");
            isValid = false;
        }
        if (!isValidImagem(imagem)) {
            toast.error("Imagem do produto é obrigatória.");
            isValid = false;
        }
        if (!isValidEstoque(estoque)) {
            toast.error("Estoque do produto é inválido.");
            isValid = false;
        }
        if (!isValidCategoriaProduto(categoriaProdutoId)) {
            toast.error("Categoria do produto é obrigatória.");
            isValid = false;
        }
        if (!statusId) {
            toast.error("Status do produto é obrigatório.");
            isValid = false;
        }

        if (!isValid) {
            return;
        }

        const produtoData = {
            nome,
            descricao,
            preco: parseFloat(preco),
            imagem,
            estoque: parseInt(estoque, 10),
            categoriaProdutoId,
            statusId
        };

        const url = editingProduto
            ? `${process.env.REACT_APP_API_URL}/produtos/${editingProduto.id}`
            : `${process.env.REACT_APP_API_URL}/produtos`;

        const method = editingProduto ? 'put' : 'post';

        axios({
            method: method,
            url: url,
            data: produtoData,
            withCredentials: true
        })
        .then(response => {
            console.log('Produto salvo com sucesso:', response.data);
            setShowModal(false);

            setProdutos(prevProdutos => {
                if (editingProduto) {
                    return prevProdutos.map(produto =>
                        produto.id === editingProduto.id ? response.data : produto
                    );
                } else {
                    return [...prevProdutos, response.data];
                }
            });

            setEditingProduto(null);
            setNome('');
            setDescricao('');
            setPreco('');
            setImagem('');
            setEstoque('');
            setCategoriaProdutoId('');
            setStatusId('');
        })
        .catch(error => {
            console.error('Erro ao salvar produto:', error);
        });
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
                        <li 
                            className={activeTab === 'vendidos' ? 'active' : ''} 
                            onClick={() => setActiveTab('vendidos')}
                        >
                            Produtos Vendidos
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