import React, { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import Header from '../../Components/Menu/Items/Header/Header';
import LojaForm from '../../Components/LojaForm/LojaForm';
import '../CSS/CreateLoja.css';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../../Util/Authentication';
import api from '../../Util/teste';

const CriarLoja = () => {
    const { isAuthenticated } = useContext(AuthContext);
    const [nome, setNome] = useState('');
    const [categoriaId, setCategoriaId] = useState('');
    const [categorias, setCategorias] = useState([]);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        if (!isAuthenticated) {
            navigate('/login');
            return;
        }

        api.get('http://localhost:8080/lojas/verificar-loja', { withCredentials: true })
            .then(response => {
                if (response.data === "Redirecionar para /minha-loja") {
                    navigate("/MinhaLoja");
                } else {
                    axios.get('http://localhost:8080/categorias-l')
                        .then(response => {
                            setCategorias(response.data);
                        })
                        .catch(error => {
                            console.error('Erro ao buscar categorias:', error);
                        });
                }
            })
            .catch(error => {
                console.error('Erro ao verificar loja:', error);
            });
    }, [isAuthenticated]);

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
        setError(''); 

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
                <LojaForm 
                    nome={nome}
                    setNome={setNome}
                    categoriaId={categoriaId}
                    setCategoriaId={setCategoriaId}
                    categorias={categorias}
                    handleSubmit={handleSubmit}
                    error={error}
                />
            </main>
        </div>
    );
};

export default CriarLoja;