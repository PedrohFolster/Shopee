import React, { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import Header from '../../Components/Menu/Items/Header/Header';
import LojaForm from '../../Components/LojaForm/LojaForm';
import '../CSS/CreateLoja.css';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../../Util/Authentication';
import { toast } from 'react-toastify';

const CriarLoja = () => {
    const { isAuthenticated } = useContext(AuthContext);
    const [nome, setNome] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        if (!isAuthenticated) {
            navigate('/login');
            return;
        }

        axios.get(`${process.env.REACT_APP_API_URL}/lojas/verificar-loja`, { withCredentials: true })
            .then(response => {
                if (response.data === "Redirecionar para /minha-loja") {
                    navigate("/MinhaLoja");
                }
            })
            .catch(error => {
                console.error('Erro ao verificar loja:', error);
            });
    }, [isAuthenticated, navigate]);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!nome) {
            setError('O nome é obrigatório.');
            return;
        }
        setError(''); 

        const novaLoja = {
            nome
        };

        axios.post(`${process.env.REACT_APP_API_URL}/lojas`, novaLoja, { withCredentials: true })
            .then(response => {
                console.log('Loja criada com sucesso:', response.data);
                navigate('/minhaloja');
            })
            .catch(error => {
                console.error('Erro ao criar loja:', error);
                const errorMessage = error.response?.data?.message || 'Erro ao criar loja. Por favor, tente novamente.';
                if (errorMessage.includes('obrigatório e deve conter pelo menos 2 caracteres')) {
                    toast.warn('O nome da loja é obrigatório e deve conter pelo menos 2 caracteres.');
                } else {
                    toast.error(errorMessage);
                }
            });
    };

    return (
        <div className="create-loja">
            <Header searchHidden={true} navbarHidden={true} />
            <main className="create-loja-form">
                <h2>CRIAR LOJA</h2>
                <p className="descricao-loja">Preencha os campos abaixo para criar sua loja e começar a vender seus produtos!</p>
                <LojaForm 
                    nome={nome}
                    setNome={setNome}
                    handleSubmit={handleSubmit}
                    error={error}
                />
            </main>
        </div>
    );
};

export default CriarLoja;