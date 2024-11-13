import React from 'react';
import FormGroup from './FormGroup';

const FormularioProduto = ({
  nome, setNome, descricao, setDescricao, preco, setPreco, imagem, setImagem,
  estoque, setEstoque, categoriaProdutoId, setCategoriaProdutoId, statusId, setStatusId,
  categorias, statusList, handleSubmit
}) => (
  <form onSubmit={handleSubmit}>
    <FormGroup>
      <input type="text" placeholder="Nome" value={nome} onChange={(e) => setNome(e.target.value)} required />
    </FormGroup>
    <FormGroup>
      <input type="number" placeholder="Preço" value={preco} onChange={(e) => setPreco(e.target.value)} required />
    </FormGroup>
    <FormGroup>
      <input type="text" placeholder="Imagem" value={imagem} onChange={(e) => setImagem(e.target.value)} />
    </FormGroup>
    <FormGroup>
      <input type="number" placeholder="Estoque" value={estoque} onChange={(e) => setEstoque(e.target.value)} required />
    </FormGroup>
    <FormGroup>
      <select value={categoriaProdutoId} onChange={(e) => setCategoriaProdutoId(e.target.value)} required>
        <option value="">Selecione a Categoria</option>
        {categorias.map(categoria => (
          <option key={categoria.id} value={categoria.id}>
            {categoria.nome}
          </option>
        ))}
      </select>
    </FormGroup>
    <FormGroup>
      <select value={statusId} onChange={(e) => setStatusId(e.target.value)} required>
        <option value="">Selecione o Status</option>
        {statusList.map(status => (
          <option key={status.id} value={status.id}>
            {status.nomeStatus}
          </option>
        ))}
      </select>
    </FormGroup>
    <FormGroup>
      <textarea placeholder="Descrição" value={descricao} onChange={(e) => setDescricao(e.target.value)} required />
    </FormGroup>
    <button type="submit">Salvar Produto</button>
  </form>
);

export default FormularioProduto; 