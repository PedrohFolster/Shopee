package com.projeto.shopee.util;

import com.projeto.shopee.dto.CategoriaProdutoDTO;
import com.projeto.shopee.entities.CategoriaProduto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class CategoriaProdutoMapper {

    public CategoriaProdutoDTO toDTO(CategoriaProduto categoriaProduto) {
        return new CategoriaProdutoDTO(categoriaProduto.getId(), categoriaProduto.getNome());
    }

    public CategoriaProduto toEntity(CategoriaProdutoDTO categoriaProdutoDTO) {
        return new CategoriaProduto(categoriaProdutoDTO.getId(), categoriaProdutoDTO.getNome());
    }

    public List<CategoriaProdutoDTO> toDTOs(List<CategoriaProduto> categorias) {
        return categorias.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
