import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Header from '../../Components/Menu/Items/Header/Header';
import Button from '../../Components/Button/Button';
import Produto from '../../Components/Product/Produto';
import '../CSS/MinhaLoja.css';
import '../CSS/CriarProduto.css';

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
    const [mensagem, setMensagem] = useState('');
    const [categorias, setCategorias] = useState([]);
    const [statusList, setStatusList] = useState([]);
    const [produtos, setProdutos] = useState([]);
    const [editingProduto, setEditingProduto] = useState(null);


    useEffect(() => {
        axios.get('http://localhost:8080/lojas/minha-loja', { withCredentials: true })
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
        }
    }, [showModal]);

    useEffect(() => {
        if (activeTab === 'produtos' && lojaInfo?.id) {
            axios.get(`http://localhost:8080/produtos/loja/${lojaInfo.id}`, { withCredentials: true })
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
        if (!nome.trim() || /\s{2,}/.test(nome)) {
            setMensagem('O campo nome está inválido.');
            return false;
        }
        if (!/^\d+$/.test(preco) || parseFloat(preco) >= 99999) {
            setMensagem('O preço está inválido. Deve ser um número abaixo de 99999.');
            return false;
        }
        if (!imagem.trim()) {
            setMensagem('O campo imagem está inválido.');
            return false;
        }
        if (!/^\d+$/.test(estoque) || parseInt(estoque, 10) >= 99999) {
            setMensagem('O campo estoque está inválido. Deve ser um número abaixo de 99999.');
            return false;
        }
        if (!categoriaProdutoId.trim()) {
            setMensagem('A categoria é obrigatória.');
            return false;
        }
        if (!statusId.trim()) {
            setMensagem('O status é obrigatório.');
            return false;
        }
        
        if (!descricao.trim() || /\s{2,}/.test(descricao) || descricao.length > 500) {
            setMensagem('O campo descrição está inválido.');
            return false;
        }
        return true;
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
            ? `http://localhost:8080/produtos/${editingProduto.id}`
            : 'http://localhost:8080/produtos';
        
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
                axios.get(`http://localhost:8080/produtos/loja/${lojaInfo.id}`, { withCredentials: true })
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

    const renderModal = () => {
        if (!showModal) return null;

        return (
            <div className="modal-overlay">
                <div className="modal-content">
                    <button className="modal-close" onClick={() => {
                        setShowModal(false);
                        setEditingProduto(null);
                        setNome('');
                        setDescricao('');
                        setPreco('');
                        setImagem('');
                        setEstoque('');
                        setCategoriaProdutoId('');
                        setStatusId('');
                    }}>×</button>
                    <div className="form-container">
                        <h2>{editingProduto ? 'Editar Produto' : 'Criar Produto'}</h2>
                        <div className="separator"></div>
                        <form onSubmit={handleSubmit}>
                            <div className="form-group">
                                <input 
                                    type="text" 
                                    placeholder="Nome" 
                                    value={nome} 
                                    onChange={(e) => setNome(e.target.value)} 
                                    required 
                                />
                            </div>
                            <div className="form-group">
                                <input 
                                    type="number" 
                                    placeholder="Preço" 
                                    value={preco} 
                                    onChange={(e) => setPreco(e.target.value)} 
                                    required 
                                />
                            </div>
                            <div className="form-group">
                                <input 
                                    type="text" 
                                    placeholder="Imagem" 
                                    value={imagem} 
                                    onChange={(e) => setImagem(e.target.value)} 
                                    required 
                                />
                            </div>
                            <div className="form-group">
                                <input 
                                    type="number" 
                                    placeholder="Estoque" 
                                    value={estoque} 
                                    onChange={(e) => setEstoque(e.target.value)} 
                                    required 
                                />
                            </div>
                            <div className="form-group">
                                <select 
                                    value={categoriaProdutoId} 
                                    onChange={(e) => setCategoriaProdutoId(e.target.value)} 
                                    required
                                >
                                    <option value="">Selecione a Categoria</option>
                                    {categorias.map(categoria => (
                                        <option key={categoria.id} value={categoria.id}>
                                            {categoria.nome}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="form-group">
                                <select 
                                    value={statusId} 
                                    onChange={(e) => setStatusId(e.target.value)} 
                                    required
                                >
                                    <option value="">Selecione o Status</option>
                                    {statusList.map(status => (
                                        <option key={status.id} value={status.id}>
                                            {status.nomeStatus}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="form-group-full">
                                <textarea 
                                    placeholder="Descrição" 
                                    value={descricao} 
                                    onChange={(e) => setDescricao(e.target.value)} 
                                    required 
                                />
                            </div>
                            <Button type="button-register" onClick={handleSubmit}>
                                Salvar Produto
                            </Button>
                        </form>
                        {mensagem && <p className="mensagem">{mensagem}</p>}
                    </div>
                </div>
            </div>
        );
    };


    const mapearStatus = (statusId) => {
        switch (statusId) {
            case 1:
                return 'Ativo';
            case 2:
                return 'Inativo';
            default:
                return 'Desconhecido';
        }
    };

    const renderContent = () => {
        switch(activeTab) {
            case 'dados':
                return (
                    <div className="informacoes-loja">
                        <p><strong>Nome da Loja:</strong> {lojaInfo?.nome || 'Informação não disponvel'}</p>
                        <p><strong>Quantidade de Produtos:</strong> {lojaInfo?.quantidadeProdutos || 0}</p>
                        <p><strong>Produtos Vendidos:</strong> {lojaInfo?.quantidadeVendidos || 0}</p>
                        <p><strong>Valor Total:</strong> R$ {lojaInfo?.valorTotal ? lojaInfo.valorTotal.toFixed(2) : '0.00'}</p>
                    </div>
                );
            case 'produtos':
                return (
                    <div className="produtos-container">
                        <div className="produtos-header">
                            <Button 
                                type="button-register" 
                                onClick={() => setShowModal(true)}
                            >
                                Cadastrar Novo Produto
                            </Button>
                        </div>
                        <div className="minha-loja-produtos-list">
                            {produtos.length === 0 ? (
                                <p>Nenhum produto cadastrado</p>
                            ) : (
                                produtos.map(produto => (
                                    <Produto 
                                        key={produto.id} 
                                        produto={produto} 
                                        onEdit={handleEditarProduto} 
                                        pageStyle="minha-loja-style"
                                    />
                                ))
                            )}
                        </div>
                    </div>
                );
            case 'estoque':
                return (
                    <div className="informacoes-loja">
                        <p>Informações do estoque virão aqui</p>
                    </div>
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
                        <li 
                            className={activeTab === 'estoque' ? 'active' : ''} 
                            onClick={() => setActiveTab('estoque')}
                        >
                            Meu Estoque
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
                {renderModal()}
            </div>
        </div>
    );
};

export default MinhaLoja;