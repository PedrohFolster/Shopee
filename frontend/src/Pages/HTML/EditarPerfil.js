import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../../Components/Menu/Items/Header/Header';
import FormularioPerfil from '../../Components/EditarPerfil/FormularioPerfil';
import MensagemErro from '../../Components/EditarPerfil/MensagemErro';
import '../CSS/Register.css';
import { formatarCpf } from '../../Util/CpfFormatter';
import { formatarTelefone } from '../../Util/TelefoneFormatter';
import { isValidNomeCompleto, isValidEmail, isValidDataNascimento, isValidTelefone } from '../../Util/ValidacoesPerfil';

function EditarPerfil() {
    const [usuario, setUsuario] = useState({
        nome: '',
        cpf: '',
        email: '',
        telefone: '',
        dataNascimento: '',
        usuarioAutenticarDTO: {
            id: '',
            passwordHash: ''
        }
    });

    const [senhaAtual, setSenhaAtual] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        axios.get('http://localhost:8080/usuarios/perfil', { withCredentials: true })
            .then(response => {
                const data = response.data;
                setUsuario({
                    ...data,
                    cpf: formatarCpf(data.cpf),
                    telefone: formatarTelefone(data.telefone)
                });
            })
            .catch(error => {
                setErrorMessage("Erro ao buscar dados do usuário.");
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

    const handleSubmit = (e) => {
        e.preventDefault();
        setErrorMessage('');

        if (!senhaAtual) {
            setErrorMessage("Por favor, informe sua senha atual.");
            return;
        }

        // Validações
        if (!isValidNomeCompleto(usuario.nome)) {
            setErrorMessage("Nome completo deve conter pelo menos dois nomes.");
            return;
        }
        if (!isValidEmail(usuario.email)) {
            setErrorMessage("E-mail inválido.");
            return;
        }
        if (!isValidDataNascimento(usuario.dataNascimento)) {
            setErrorMessage("Data de nascimento inválida ou usuário deve ter pelo menos 12 anos.");
            return;
        }
        if (!isValidTelefone(usuario.telefone)) {
            setErrorMessage("Telefone inválido.");
            return;
        }

        axios.post('http://localhost:8080/usuarios/validar-senha', null, {
            params: { senha: senhaAtual },
            withCredentials: true
        })
        .then(response => {
            if (response.data.valid) {
                const usuarioSemFormatacao = {
                    ...usuario,
                    telefone: usuario.telefone.replace(/\D/g, ''),
                    usuarioAutenticarDTO: { 
                        ...usuario.usuarioAutenticarDTO,
                        passwordHash: undefined
                    }
                };
                axios.put(`http://localhost:8080/usuarios/${usuario.id}`, usuarioSemFormatacao, { withCredentials: true })
                    .then(response => {
                        alert("Perfil atualizado com sucesso!");
                    })
                    .catch(error => {
                        setErrorMessage("Erro ao atualizar perfil.");
                    });
            } else {
                setErrorMessage("Senha incorreta.");
            }
        })
        .catch(error => {
            setErrorMessage("Erro ao validar senha.");
        });
    };

    return (
        <div className="register">
            <Header searchHidden={true} navbarHidden={true}/>
            <main className="register-content">
                <h2>EDITAR PERFIL</h2>
                <div className="separator"></div>
                {errorMessage && <MensagemErro mensagem={errorMessage} />}
                <FormularioPerfil 
                    usuario={usuario}
                    handleChange={handleChange}
                    senhaAtual={senhaAtual}
                    setSenhaAtual={setSenhaAtual}
                    handleSubmit={handleSubmit}
                />
            </main>
        </div>
    );
}

export default EditarPerfil; 