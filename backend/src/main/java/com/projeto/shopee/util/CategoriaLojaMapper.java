package com.projeto.shopee.util;

import com.projeto.shopee.dto.CategoriaLojaDTO;
import com.projeto.shopee.entities.CategoriaLoja;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoriaLojaMapper {

    public CategoriaLojaDTO toDTO(CategoriaLoja categoriaLoja) {
        return new CategoriaLojaDTO(categoriaLoja.getId(), categoriaLoja.getNome());
    }

    public CategoriaLoja toEntity(CategoriaLojaDTO categoriaLojaDTO) {
        return new CategoriaLoja(categoriaLojaDTO.getId(), categoriaLojaDTO.getNome());
    }

    public List<CategoriaLojaDTO> toDTOs(List<CategoriaLoja> categoriasLoja) {
        return categoriasLoja.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
