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
        setUsuario(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!senhaAtual) {
            setErrorMessage("Por favor, informe sua senha atual.");
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
                    usuarioAutenticarDTO: { ...usuario.usuarioAutenticarDTO }
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
                            required
                        />
                    </div>
                    <div className="form-row-register">
                        <Input
                            type="text"
                            name="cpf"
                            value={usuario.cpf}
                            readOnly
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
                            name="senhaAtual"
                            value={senhaAtual}
                            onChange={(e) => setSenhaAtual(e.target.value)}
                            placeholder="Senha atual*"
                            required
                        />
                    </div>
                    <Button type="submit">Atualizar Perfil</Button>
                </form>
            </main>
        </div>
    );
}

export default EditarPerfil;
