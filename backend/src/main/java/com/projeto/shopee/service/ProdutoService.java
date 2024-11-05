package com.projeto.shopee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.shopee.dto.ProdutoDTO;
import com.projeto.shopee.entities.Loja;
import com.projeto.shopee.entities.Produto;
import com.projeto.shopee.repository.LojaRepository;
import com.projeto.shopee.repository.ProdutoRepository;
import com.projeto.shopee.util.ProdutoMapper;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    public List<ProdutoDTO> getAllProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtoMapper.toDTOs(produtos);
    }

    public ProdutoDTO getProdutoById(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.map(produtoMapper::toDTO).orElse(null);
    }

    public ProdutoDTO createProduto(ProdutoDTO produtoDTO, Long usuarioId) {
        Loja loja = lojaRepository.findByUsuarioId(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário não possui uma loja"));

        Produto produto = produtoMapper.toEntity(produtoDTO);
        produto.setLoja(loja);
        produto = produtoRepository.save(produto);
        return produtoMapper.toDTO(produto);
    }

    public ProdutoDTO updateProduto(Long id, ProdutoDTO produtoDTO) {
        if (!produtoRepository.existsById(id)) {
            return null;
        }
        produtoDTO.setId(id);
        Produto produto = produtoMapper.toEntity(produtoDTO); 
        produto = produtoRepository.save(produto);
        return produtoMapper.toDTO(produto);
    }

    public void deleteProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public List<ProdutoDTO> getProdutosByLojaId(Long lojaId) {
        List<Produto> produtos = produtoRepository.findByLojaId(lojaId);
        return produtoMapper.toDTOs(produtos);
    }

    public List<ProdutoDTO> getProdutosByLojaIdAndUsuario(Long lojaId, Long usuarioId) {
        Loja loja = lojaRepository.findByIdAndUsuarioId(lojaId, usuarioId)
            .orElseThrow(() -> new RuntimeException("Loja não encontrada ou não pertence ao usuário"));

        List<Produto> produtos = produtoRepository.findByLojaId(lojaId);
        return produtoMapper.toDTOs(produtos);
    }
}