import React from 'react';
import PropTypes from 'prop-types';
import Button from '../../Components/Button/Button';

const ProdutoModal = ({
  showModal,
  setShowModal,
  editingProduto,
  setEditingProduto,
  nome,
  setNome,
  descricao,
  setDescricao,
  preco,
  setPreco,
  imagem,
  setImagem,
  estoque,
  setEstoque,
  categoriaProdutoId,
  setCategoriaProdutoId,
  statusId,
  setStatusId,
  categorias = [],
  statusList = [], 
  handleSubmit,
  mensagem
}) => {
  if (!showModal) return null;

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <button className="modal-close" onClick={() => {
          setShowModal(false);
          setEditingProduto(null);
          setNome('');
          setDescricao('');
          setPreco('');
          setImagem('');
          setEstoque('');
          setCategoriaProdutoId('');
          setStatusId('');
        }}>×</button>
        <div className="form-container">
          <h2>{editingProduto ? 'Editar Produto' : 'Criar Produto'}</h2>
          <div className="separator"></div>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <input
                type="text"
                placeholder="Nome"
                value={nome}
                onChange={(e) => setNome(e.target.value)}
                required
              />
            </div>
            <div className="form-group">
              <input
                type="number"
                placeholder="Preço"
                value={preco}
                onChange={(e) => setPreco(e.target.value)}
                required
              />
            </div>
            <div className="form-group">
              <input
                type="text"
                placeholder="Imagem"
                value={imagem}
                onChange={(e) => setImagem(e.target.value)}
                required
              />
            </div>
            <div className="form-group">
              <input
                type="number"
                placeholder="Estoque"
                value={estoque}
                onChange={(e) => setEstoque(e.target.value)}
                required
              />
            </div>
            <div className="form-group">
              <select
                value={categoriaProdutoId}
                onChange={(e) => setCategoriaProdutoId(e.target.value)}
                required
              >
                <option value="">Selecione a Categoria</option>
                {Array.isArray(categorias) && categorias.map(categoria => (
                  <option key={categoria.id} value={categoria.id}>
                    {categoria.nome}
                  </option>
                ))}
              </select>
            </div>
            <div className="form-group">
              <select
                value={statusId}
                onChange={(e) => setStatusId(e.target.value)}
                required
              >
                <option value="">Selecione o Status</option>
                {Array.isArray(statusList) && statusList.map(status => (
                  <option key={status.id} value={status.id}>
                    {status.nomeStatus}
                  </option>
                ))}
              </select>
            </div>
            <div className="form-group-full">
              <textarea
                placeholder="Descrição"
                value={descricao}
                onChange={(e) => setDescricao(e.target.value)}
                required
              />
            </div>
            <Button type="button-register" onClick={handleSubmit}>
              Salvar Produto
            </Button>
          </form>
          {mensagem && <p className="mensagem">{mensagem}</p>}
        </div>
      </div>
    </div>
  );
};

ProdutoModal.propTypes = {
  showModal: PropTypes.bool.isRequired,
  setShowModal: PropTypes.func.isRequired,
  editingProduto: PropTypes.object,
  setEditingProduto: PropTypes.func.isRequired,
  nome: PropTypes.string.isRequired,
  setNome: PropTypes.func.isRequired,
  descricao: PropTypes.string.isRequired,
  setDescricao: PropTypes.func.isRequired,
  preco: PropTypes.string.isRequired,
  setPreco: PropTypes.func.isRequired,
  imagem: PropTypes.string.isRequired,
  setImagem: PropTypes.func.isRequired,
  estoque: PropTypes.string.isRequired,
  setEstoque: PropTypes.func.isRequired,
  categoriaProdutoId: PropTypes.string.isRequired,
  setCategoriaProdutoId: PropTypes.func.isRequired,
  statusId: PropTypes.string.isRequired,
  setStatusId: PropTypes.func.isRequired,
  categorias: PropTypes.array.isRequired,
  statusList: PropTypes.array.isRequired,
  handleSubmit: PropTypes.func.isRequired,
  mensagem: PropTypes.string,
};

export default ProdutoModal;