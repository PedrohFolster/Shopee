import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../../Components/Menu/Items/Header/Header';
import FormularioPerfil from '../../Components/EditarPerfil/FormularioPerfil';
import MensagemErro from '../../Components/EditarPerfil/MensagemErro';
import ModalSenha from '../../Components/EditarPerfil/ModalSenha';
import ModalEndereco from '../../Components/EditarPerfil/ModalEndereco';
import '../CSS/EditarPerfil.css';
import { formatarCpf } from '../../Util/CpfFormatter';
import { formatarTelefone } from '../../Util/TelefoneFormatter';
import { isValidNomeCompleto, isValidEmail, isValidDataNascimento, isValidTelefone } from '../../Util/ValidacoesPerfil';
import { toast } from 'react-toastify';

function EditarPerfil() {
    const [usuario, setUsuario] = useState({
        nome: '',
        cpf: '',
        email: '',
        telefone: '',
        dataNascimento: '',
        usuarioAutenticarDTO: {
            id: '',
            password: ''
        },
        enderecoDTO: {
            rua: '',
            numero: '',
            cidade: '',
            estado: '',
            pais: '',
            complemento: ''
        }
    });

    const [senhaAtual, setSenhaAtual] = useState('');
    const [novaSenha, setNovaSenha] = useState('');
    const [confirmarNovaSenha, setConfirmarNovaSenha] = useState('');
    const [enderecoSenha, setEnderecoSenha] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [isSenhaModalOpen, setIsSenhaModalOpen] = useState(false);
    const [isEnderecoModalOpen, setIsEnderecoModalOpen] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem('token');
        axios.get('http://localhost:8080/usuarios/perfil', {
            headers: { 'Authorization': `Bearer ${token}` },
            withCredentials: true
        })
        .then(response => {
            const data = response.data;
            setUsuario({
                ...data,
                cpf: formatarCpf(data.cpf),
                telefone: formatarTelefone(data.telefone)
            });
        })
        .catch(error => {
            toast.error("Erro ao buscar dados do usuário.");
        });
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
    
        if (name === 'telefone') {
            if (value.replace(/\D/g, '').length <= 11) {
                setUsuario(prevState => ({
                    ...prevState,
                    [name]: formatarTelefone(value)
                }));
            }
        } else {
            setUsuario(prevState => ({
                ...prevState,
                [name]: value
            }));
        }
    };

    const handleSubmitSenha = (e) => {
        e.preventDefault();
        if (novaSenha !== confirmarNovaSenha) {
            toast.error("As senhas não coincidem.");
            return;
        }

        const token = localStorage.getItem('token');
        axios.put(`http://localhost:8080/usuarios/${usuario.id}/senha`, null, {
            headers: { 'Authorization': `Bearer ${token}` },
            params: { senhaAtual, novaSenha },
            withCredentials: true
        })
        .then(response => {
            toast.success("Senha atualizada com sucesso!");
            setIsSenhaModalOpen(false);
        })
        .catch(error => {
            if (error.response && error.response.data && error.response.data.message) {
                toast.error(error.response.data.message);
            } else {
                toast.error("Erro ao atualizar senha.");
            }
        });
    };

    const handleSubmitEndereco = (e) => {
        e.preventDefault();

        const token = localStorage.getItem('token');
        axios.put(`http://localhost:8080/usuarios/${usuario.id}/endereco`, usuario.enderecoDTO, {
            headers: { 'Authorization': `Bearer ${token}` },
            params: { senha: enderecoSenha },
            withCredentials: true
        })
        .then(response => {
            toast.success("Endereço atualizado com sucesso!");
            setIsEnderecoModalOpen(false);
        })
        .catch(error => {
            if (error.response && error.response.data && error.response.data.message) {
                toast.error(error.response.data.message);
            } else {
                toast.error("Erro ao atualizar endereço.");
            }
        });
    };

    return (
        <div className="register">
            <Header searchHidden={true} navbarHidden={true}/>
            <main className="register-content">
                <h2>EDITAR PERFIL</h2>
                <div className="separator"></div>
                {errorMessage && <MensagemErro mensagem={errorMessage} />}
                <div className="button-container">
                    <button className="button-register" onClick={() => setIsSenhaModalOpen(true)}>Mudar Senha</button>
                    <button className="button-register" onClick={() => setIsEnderecoModalOpen(true)}>Editar Endereço</button>
                </div>
                <FormularioPerfil 
                    usuario={usuario}
                    handleChange={handleChange}
                    senhaAtual={senhaAtual}
                    setSenhaAtual={setSenhaAtual}
                    handleSubmit={handleSubmitSenha}
                />
                <ModalSenha
                    isOpen={isSenhaModalOpen}
                    onClose={() => setIsSenhaModalOpen(false)}
                    senhaAtual={senhaAtual}
                    setSenhaAtual={setSenhaAtual}
                    novaSenha={novaSenha}
                    setNovaSenha={setNovaSenha}
                    confirmarNovaSenha={confirmarNovaSenha}
                    setConfirmarNovaSenha={setConfirmarNovaSenha}
                    handleSubmit={handleSubmitSenha}
                />
                <ModalEndereco
                    isOpen={isEnderecoModalOpen}
                    onClose={() => setIsEnderecoModalOpen(false)}
                    enderecoDTO={usuario.enderecoDTO}
                    setEnderecoDTO={(newEndereco) => setUsuario({...usuario, enderecoDTO: newEndereco})}
                    enderecoSenha={enderecoSenha}
                    setEnderecoSenha={setEnderecoSenha}
                    handleSubmit={handleSubmitEndereco}
                />
            </main>
        </div>
    );
}

export default EditarPerfil;
