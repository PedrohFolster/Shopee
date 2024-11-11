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
        enderecoDTO: {
            id: '',
            cep: '',
            rua: '',
            numero: '',
            cidade: '',
            estado: '',
            pais: '',
            complemento: ''
        },
        usuarioAutenticarDTO: {
            id: '',
            username: '',
            passwordHash: ''
        }
    });

    const [novaSenha, setNovaSenha] = useState('');
    const [confirmarSenha, setConfirmarSenha] = useState('');
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
        if (name === 'cpf') {
            if (value.replace(/\D/g, '').length <= 11) {
                setUsuario(prevState => ({
                    ...prevState,
                    [name]: formatarCpf(value)
                }));
            }
        } else if (name === 'telefone') {
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
        if (!validarCpf(usuario.cpf)) {
            setErrorMessage("CPF inválido.");
            return;
        }
        if (novaSenha || confirmarSenha) {
            if (novaSenha !== confirmarSenha) {
                setErrorMessage("As senhas não coincidem.");
                return;
            }
            if (!validarSenha(novaSenha)) {
                setErrorMessage("Senha inválida. Deve conter pelo menos uma letra maiúscula, letras, números e um caractere especial.");
                return;
            }
            usuario.usuarioAutenticarDTO.passwordHash = novaSenha; // Substitua por hash real
        }
        const usuarioSemFormatacao = {
            ...usuario,
            cpf: usuario.cpf.replace(/\D/g, ''),
            telefone: usuario.telefone.replace(/\D/g, ''),
            enderecoDTO: { ...usuario.enderecoDTO },
            usuarioAutenticarDTO: { ...usuario.usuarioAutenticarDTO }
        };
        axios.put(`http://localhost:8080/usuarios/${usuario.id}`, usuarioSemFormatacao, { withCredentials: true })
            .then(response => {
                alert("Perfil atualizado com sucesso!");
            })
            .catch(error => {
                setErrorMessage("Erro ao atualizar perfil.");
            });
    };

    const validarSenha = (senha) => {
        const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        return regex.test(senha);
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
                            required
                        />
                    </div>
                    <div className="form-row-register">
                        <Input
                            type="text"
                            name="cpf"
                            value={usuario.cpf}
                            onChange={handleChange}
                            placeholder="CPF*"
                            required
                        />
                        <Input
                            type="email"
                            name="email"
                            value={usuario.email}
                            onChange={handleChange}
                            placeholder="E-mail*"
                            required
                        />
                    </div>
                    <div className="form-row-register">
                        <Input
                            type="date"
                            name="dataNascimento"
                            value={usuario.dataNascimento}
                            onChange={handleChange}
                            placeholder="Data de nascimento*"
                            required
                        />
                        <Input
                            type="tel"
                            name="telefone"
                            value={usuario.telefone}
                            onChange={handleChange}
                            placeholder="Telefone*"
                            required
                        />
                    </div>
                    <div className="form-row completo">
                        <Input
                            type="password"
                            name="novaSenha"
                            value={novaSenha}
                            onChange={(e) => setNovaSenha(e.target.value)}
                            placeholder="Nova senha"
                        />
                    </div>
                    <div className="form-row completo">
                        <Input
                            type="password"
                            name="confirmarSenha"
                            value={confirmarSenha}
                            onChange={(e) => setConfirmarSenha(e.target.value)}
                            placeholder="Confirme sua senha"
                        />
                    </div>
                    <Button type="submit">Atualizar Perfil</Button>
                </form>
            </main>
        </div>
    );
}

export default EditarPerfil;
