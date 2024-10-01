package com.projeto.shopee.util;

import com.projeto.shopee.dto.ProdutoDTO;
import com.projeto.shopee.entities.Produto;
import com.projeto.shopee.entities.Status;
import com.projeto.shopee.entities.Loja;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoMapper {

    public ProdutoDTO toDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getImagem(),
                produto.getEstoque(),
                produto.getStatus().getId(), // Mapeando Status
                produto.getCategoriaProduto().getId(),
                produto.getLoja().getId() // Mapeando Loja
        );
    }

    public Produto toEntity(ProdutoDTO produtoDTO, Status status, Loja loja) {
        Produto produto = new Produto();
        produto.setId(produtoDTO.getId());
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());
        produto.setImagem(produtoDTO.getImagem());
        produto.setEstoque(produtoDTO.getEstoque());
        produto.setStatus(status); // Definindo Status
        produto.setLoja(loja); // Definindo Loja
        return produto;
    }

    public List<ProdutoDTO> toDTOs(List<Produto> produtos) {
        return produtos.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
