import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../../Components/Menu/Items/Header/Header';
import Input from '../../Components/Input/Input';
import Button from '../../Components/Button/Button';
import '../CSS/CreateLoja.css';
import { useNavigate } from 'react-router-dom';

const CriarLoja = () => {
    const [nome, setNome] = useState('');
    const [categoriaId, setCategoriaId] = useState('');
    const [categorias, setCategorias] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get('http://localhost:8080/categorias-l')
            .then(response => {
                setCategorias(response.data);
            })
            .catch(error => {
                console.error('Erro ao buscar categorias:', error);
            });
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();
        const novaLoja = {
            nome,
            categoriaLojaId: categoriaId
        };

        axios.post('http://localhost:8080/lojas', novaLoja, { withCredentials: true })
            .then(response => {
                console.log('Loja criada com sucesso:', response.data);
                navigate('/minhaloja');
            })
            .catch(error => {
                console.error('Erro ao criar loja:', error);
                alert('Erro ao criar loja. Por favor, tente novamente.');
            });
    };

    return (
        <div className="create-loja">
            <Header searchHidden={true} />
            <main className="create-loja-content centered-form">
                <h2>CRIAR LOJA</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-row">
                        <Input
                            type="text"
                            id="nome"
                            name="nome"
                            value={nome}
                            onChange={(e) => setNome(e.target.value)}
                            placeholder="Nome da Loja*"
                            required
                            className="loja-nome-input"
                        />
                    </div>
                    <div className="form-row">
                        <select
                            className="loja-categoria-select"
                            value={categoriaId}
                            onChange={(e) => setCategoriaId(e.target.value)}
                            required
                        >
                            <option value="">Selecione uma categoria*</option>
                            {categorias.map(categoria => (
                                <option key={categoria.id} value={categoria.id}>
                                    {categoria.nome}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="form-row">
                        <Button type="criar-loja-btn" onClick={handleSubmit}>
                            Criar Loja
                        </Button>
                    </div>
                </form>
            </main>
        </div>
    );
};

export default CriarLoja;