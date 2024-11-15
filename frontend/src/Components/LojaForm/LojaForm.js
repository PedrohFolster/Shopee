import React from 'react';
import Input from '../Input/Input';
import Button from '../Button/Button';
import FormRow from './FormRow';
import ErrorMessage from './ErrorMessage';

const LojaForm = ({ nome, setNome, categoriaId, setCategoriaId, categorias, handleSubmit, error }) => (
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
        <FormRow>
            <select
                className="categoria-loja-select"
                value={categoriaId}
                onChange={(e) => setCategoriaId(e.target.value)}
                required
            >
                <option value="">Selecione uma categoria para sua loja</option>
                {categorias.map(categoria => (
                    <option key={categoria.id} value={categoria.id}>
                        {categoria.nome}
                    </option>
                ))}
            </select>
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