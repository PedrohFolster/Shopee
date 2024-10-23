import React, { useState, useEffect } from 'react';
import axios from 'axios';

const CriarLoja = () => {
    const [nome, setNome] = useState('');
    const [categoriaId, setCategoriaId] = useState('');
    const [categorias, setCategorias] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/categorias-l')
            .then(response => {
                setCategorias(response.data);
            })
            .catch(error => {
                console.error('Erro ao buscar categorias:', error);
            });
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();
        const novaLoja = { 
            nome, 
            categoriaLojaId: categoriaId,
            usuarioId: 1 // Adiciona o usuarioId fixo
        };

        // Enviar dados para o backend
        axios.post('http://localhost:8080/lojas', novaLoja)
            .then(response => {
                console.log('Loja criada com sucesso:', response.data);
                // Redirecionar ou mostrar mensagem de sucesso
            })
            .catch(error => {
                console.error('Erro ao criar loja:', error);
            });
    };

    return (
        <div>
            <h2>Criar Loja</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Nome da Loja:</label>
                    <input
                        type="text"
                        value={nome}
                        onChange={(e) => setNome(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Categoria:</label>
                    <select
                        value={categoriaId}
                        onChange={(e) => setCategoriaId(e.target.value)}
                        required
                    >
                        <option value="">Selecione uma categoria</option>
                        {categorias.map(categoria => (
                            <option key={categoria.id} value={categoria.id}>
                                {categoria.nome}
                            </option>
                        ))}
                    </select>
                </div>
                <button type="submit">Criar Loja</button>
            </form>
        </div>
    );
};

export default CriarLoja;
