import React from 'react';
import { Range } from 'react-range';
import PropTypes from 'prop-types';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeft, faArrowRight } from '@fortawesome/free-solid-svg-icons';

const FiltroProdutos = ({ filtros, setFiltros, categorias, handleFiltroChange, limparFiltros, paginaAtual, handleAvancarPagina, handleRetornarPagina, produtosFiltrados, produtosPorPagina }) => {
  return (
    <aside className='filtros'>
      <h3>Filtrar Produtos</h3>
      <div className='filtro-item'>
        <label>Nome do Produto:</label>
        <input
          type='text'
          name='nome'
          value={filtros.nome}
          onChange={handleFiltroChange}
          placeholder='Buscar por nome'
        />
      </div>
      <div className='filtro-item'>
        <label>Faixa de Preço:</label>
        <Range
          step={100}
          min={0}
          max={10000}
          values={filtros.preco}
          onChange={(values) => setFiltros({ ...filtros, preco: values })}
          renderTrack={({ props, children }) => (
            <div {...props} style={{ ...props.style, height: '6px', background: '#ddd' }}>
              {children}
            </div>
          )}
          renderThumb={({ props }, index) => (
            <div key={index} {...props} style={{ ...props.style, height: '20px', width: '20px', background: '#999', borderRadius: '50%' }} />
          )}
        />
        <div className='preco-inputs'>
          <div className='preco-input'>
            <label>Mínimo</label>
            <input
              type='number'
              name='precoMin'
              value={filtros.preco[0]}
              onChange={e => setFiltros({ ...filtros, preco: [parseFloat(e.target.value), filtros.preco[1]] })}
            />
          </div>
          <div className='preco-input'>
            <label>Máximo</label>
            <input
              type='number'
              name='precoMax'
              value={filtros.preco[1]}
              onChange={e => setFiltros({ ...filtros, preco: [filtros.preco[0], parseFloat(e.target.value)] })}
            />
          </div>
        </div>
      </div>
      <div className='filtro-item'>
        <label>Categoria:</label>
        <select
          name='categoria'
          value={filtros.categoria}
          onChange={handleFiltroChange}
        >
          <option value=''>Todas</option>
          {categorias.map(categoria => (
            <option key={categoria.id} value={categoria.id}>
              {categoria.nome}
            </option>
          ))}
        </select>
      </div>
      <button onClick={limparFiltros}>Limpar Filtros</button>
      <div className="paginacao">
        <button className="botao-paginacao" onClick={handleRetornarPagina} disabled={paginaAtual === 1}>
          <FontAwesomeIcon icon={faArrowLeft} />
        </button>
        <span className="pagina-atual">{paginaAtual}</span>
        <button className="botao-paginacao" onClick={handleAvancarPagina} disabled={paginaAtual * produtosPorPagina >= produtosFiltrados.length}>
          <FontAwesomeIcon icon={faArrowRight} />
        </button>
      </div>
    </aside>
  );
};

FiltroProdutos.propTypes = {
  filtros: PropTypes.object.isRequired,
  setFiltros: PropTypes.func.isRequired,
  categorias: PropTypes.array.isRequired,
  handleFiltroChange: PropTypes.func.isRequired,
  limparFiltros: PropTypes.func.isRequired,
  paginaAtual: PropTypes.number.isRequired,
  handleAvancarPagina: PropTypes.func.isRequired,
  handleRetornarPagina: PropTypes.func.isRequired,
  produtosFiltrados: PropTypes.array.isRequired,
  produtosPorPagina: PropTypes.number.isRequired,
};

export default FiltroProdutos;