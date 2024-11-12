import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../../Components/Menu/Items/Header/Header';
import Input from '../../Components/Input/Input';
import Button from '../../Components/Button/Button';
import '../CSS/Register.css';
import { formatarCpf, validarCpf } from '../../Util/CpfFormatter';
import { formatarTelefone } from '../../Util/TelefoneFormatter';

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

    // Funções de validação
    const isValidNomeCompleto = (nome) => {
        return nome && nome.split(' ').length >= 2;
    };

    const isValidEmail = (email) => {
        return email && /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    };

    const isValidDataNascimento = (dataNascimento) => {
        if (!dataNascimento) return false;
        const birthDate = new Date(dataNascimento);
        const today = new Date();
        const age = today.getFullYear() - birthDate.getFullYear();
        const monthDiff = today.getMonth() - birthDate.getMonth();
        if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
            return age - 1 >= 12;
        }
        return age >= 12;
    };

    const isValidTelefone = (telefone) => {
        return telefone && /^\d{11}$/.test(telefone.replace(/\D/g, ''));
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
                {errorMessage && <div className="error-message">{errorMessage}</div>}
                <form onSubmit={handleSubmit}>
                    <div className="form-row completo">
                        <Input
                            type="text"
                            name="nome"
                            value={usuario.nome}
                            onChange={handleChange}
                            placeholder="Nome completo*"
                        />
                    </div>
                    <div className="form-row-register">
                        <Input
                            type="text"
                            name="cpf"
                            value={usuario.cpf}
                            readOnly
                            placeholder="CPF*"
                            
                        />
                        <Input
                            type="email"
                            name="email"
                            value={usuario.email}
                            onChange={handleChange}
                            placeholder="E-mail*"
                            
                        />
                    </div>
                    <div className="form-row-register">
                        <Input
                            type="date"
                            name="dataNascimento"
                            value={usuario.dataNascimento}
                            onChange={handleChange}
                            placeholder="Data de nascimento*"
                            
                        />
                        <Input
                            type="tel"
                            name="telefone"
                            value={usuario.telefone}
                            onChange={handleChange}
                            placeholder="Telefone*"
                            
                        />
                    </div>
                    <div className="form-row completo">
                        <Input
                            type="password"
                            name="senhaAtual"
                            value={senhaAtual}
                            onChange={(e) => setSenhaAtual(e.target.value)}
                            placeholder="Senha atual*"
                        />
                    </div>
                    <Button type="submit">Atualizar Perfil</Button>
                </form>
            </main>
        </div>
    );
}

export default EditarPerfil;
