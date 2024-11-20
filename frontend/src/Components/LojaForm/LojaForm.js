import React from 'react';
import Input from '../Input/Input';
import Button from '../Button/Button';
import FormRow from './FormRow';
import ErrorMessage from './ErrorMessage';

const LojaForm = ({ nome, setNome, handleSubmit, error }) => (
    <form onSubmit={handleSubmit}>
        <FormRow>
            <Input
                type="text"
                id="nome"
                name="nome"
                value={nome}
                onChange={(e) => setNome(e.target.value)}
                placeholder="Nome da Loja"
                required
            />
        </FormRow>
        {error && <ErrorMessage message={error} />}
        <FormRow>
            <Button type="criar-loja-btn" onClick={handleSubmit}>
                Criar Loja
            </Button>
        </FormRow>
    </form>
);

export default LojaForm; 