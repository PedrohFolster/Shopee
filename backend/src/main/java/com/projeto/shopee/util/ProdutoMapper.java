package com.projeto.shopee.util;

import com.projeto.shopee.dto.LojaDTO;
import com.projeto.shopee.dto.ProdutoDTO;
import com.projeto.shopee.entities.Produto;
import com.projeto.shopee.entities.Status;
import com.projeto.shopee.repository.CategoriaProdutoRepository;
import com.projeto.shopee.repository.LojaRepository;
import com.projeto.shopee.repository.StatusRepository;
import com.projeto.shopee.entities.CategoriaProduto;
import com.projeto.shopee.entities.Loja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoMapper {

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private StatusRepository statusRepository;

    public ProdutoDTO toDTO(Produto produto) {
        LojaDTO lojaDTO = new LojaDTO();
        lojaDTO.setId(produto.getLoja().getId());
        lojaDTO.setNome(produto.getLoja().getNome());
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produto.getId());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setDescricao(produto.getDescricao());
        produtoDTO.setPreco(produto.getPreco());
        produtoDTO.setImagem(produto.getImagem());
        produtoDTO.setEstoque(produto.getEstoque());
        if (produto.getLoja() != null) {
            produtoDTO.setLojaId(produto.getLoja().getId());
        }
        if (produto.getCategoriaProduto() != null) {
            produtoDTO.setCategoriaProdutoId(produto.getCategoriaProduto().getId());
        }

        if (produto.getStatus() != null) {
            produtoDTO.setStatusId(produto.getStatus().getId());
        }

        return produtoDTO;
    }

    public Produto toEntity(ProdutoDTO produtoDTO) {
        Produto produto = new Produto();
        produto.setId(produtoDTO.getId());
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());
        produto.setImagem(produtoDTO.getImagem());
        produto.setEstoque(produtoDTO.getEstoque());

        if (produtoDTO.getCategoriaProdutoId() != null) {
            CategoriaProduto categoriaProduto = categoriaProdutoRepository.findById(produtoDTO.getCategoriaProdutoId())
                    .orElse(null);
            produto.setCategoriaProduto(categoriaProduto);
        }

        if (produtoDTO.getLojaId() != null) {
            Loja loja = lojaRepository.findById(produtoDTO.getLojaId()).orElse(null);
            produto.setLoja(loja);
        }

        if (produtoDTO.getStatusId() != null) {
            Status status = statusRepository.findById(produtoDTO.getStatusId()).orElse(null);
            produto.setStatus(status);
        }
        return produto;
    }

    public List<ProdutoDTO> toDTOs(List<Produto> produtos) {
        return produtos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<Produto> toEntities(List<ProdutoDTO> produtoDTOs) {
        return produtoDTOs.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
