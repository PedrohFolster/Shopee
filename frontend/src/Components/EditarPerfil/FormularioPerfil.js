import React from 'react';
import Input from '../Input/Input';
import Button from '../Button/Button';

const FormularioPerfil = ({ usuario, handleChange, senhaAtual, setSenhaAtual, handleSubmit }) => (
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
);

export default FormularioPerfil;
