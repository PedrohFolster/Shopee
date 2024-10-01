package com.projeto.shopee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projeto.shopee.dto.CategoriaLojaDTO;
import com.projeto.shopee.entities.CategoriaLoja;
import com.projeto.shopee.repository.CategoriaLojaRepository;
import com.projeto.shopee.util.CategoriaLojaMapper;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaLojaService {

    @Autowired
    private CategoriaLojaRepository categoriaLojaRepository;

    @Autowired
    private CategoriaLojaMapper categoriaLojaMapper;

    public List<CategoriaLojaDTO> getAllCategorias() {
        List<CategoriaLoja> categorias = categoriaLojaRepository.findAll();
        return categoriaLojaMapper.toDTOs(categorias);
    }

    public CategoriaLojaDTO getCategoriaById(Long id) {
        Optional<CategoriaLoja> categoria = categoriaLojaRepository.findById(id);
        return categoria.map(categoriaLojaMapper::toDTO).orElse(null);
    }

    public CategoriaLojaDTO createCategoria(CategoriaLojaDTO categoriaLojaDTO) {
        CategoriaLoja categoria = categoriaLojaMapper.toEntity(categoriaLojaDTO);
        categoria = categoriaLojaRepository.save(categoria);
        return categoriaLojaMapper.toDTO(categoria);
    }

    public CategoriaLojaDTO updateCategoria(Long id, CategoriaLojaDTO categoriaLojaDTO) {
        if (!categoriaLojaRepository.existsById(id)) {
            return null;
        }
        categoriaLojaDTO.setId(id);
        CategoriaLoja categoria = categoriaLojaMapper.toEntity(categoriaLojaDTO);
        categoria = categoriaLojaRepository.save(categoria);
        return categoriaLojaMapper.toDTO(categoria);
    }

    public void deleteCategoria(Long id) {
        categoriaLojaRepository.deleteById(id);
    }
}
