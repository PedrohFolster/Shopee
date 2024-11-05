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
    const [error, setError] = useState('');
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
        if (!nome) {
            setError('O nome é obrigatório.');
            return;
        }
        if (!categoriaId) {
            setError('A categoria é obrigatória.');
            return;
        }
        setError(''); // Limpa o erro se ambos os campos estiverem preenchidos

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
            <Header searchHidden={true} navbarHidden={true} />
            <main className="create-loja-form">
                <h2>CRIAR LOJA</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-row-createloja">
                        <Input
                            type="text"
                            id="nome"
                            name="nome"
                            value={nome}
                            onChange={(e) => setNome(e.target.value)}
                            placeholder="Nome da Loja*"
                            required
                        />
                    </div>
                    <div className="form-row-createloja">
                        <select
                            className="categoria-loja-select"
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
                    {error && <div className="error-message">{error}</div>}
                    <div className="form-row-button-createloja">
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