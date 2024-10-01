package com.projeto.shopee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projeto.shopee.dto.CategoriaProdutoDTO;
import com.projeto.shopee.entities.CategoriaProduto;
import com.projeto.shopee.repository.CategoriaProdutoRepository;
import com.projeto.shopee.util.CategoriaProdutoMapper;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaProdutoService {

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @Autowired
    private CategoriaProdutoMapper categoriaProdutoMapper;

    public List<CategoriaProdutoDTO> getAllCategorias() {
        List<CategoriaProduto> categorias = categoriaProdutoRepository.findAll();
        return categoriaProdutoMapper.toDTOs(categorias);
    }

    public CategoriaProdutoDTO getCategoriaById(Long id) {
        Optional<CategoriaProduto> categoria = categoriaProdutoRepository.findById(id);
        return categoria.map(categoriaProdutoMapper::toDTO).orElse(null);
    }

    public CategoriaProdutoDTO createCategoria(CategoriaProdutoDTO categoriaProdutoDTO) {
        CategoriaProduto categoria = categoriaProdutoMapper.toEntity(categoriaProdutoDTO);
        categoria = categoriaProdutoRepository.save(categoria);
        return categoriaProdutoMapper.toDTO(categoria);
    }

    public CategoriaProdutoDTO updateCategoria(Long id, CategoriaProdutoDTO categoriaProdutoDTO) {
        if (!categoriaProdutoRepository.existsById(id)) {
            return null;
        }
        categoriaProdutoDTO.setId(id);
        CategoriaProduto categoria = categoriaProdutoMapper.toEntity(categoriaProdutoDTO);
        categoria = categoriaProdutoRepository.save(categoria);
        return categoriaProdutoMapper.toDTO(categoria);
    }

    public void deleteCategoria(Long id) {
        categoriaProdutoRepository.deleteById(id);
    }
}
