package com.projeto.shopee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projeto.shopee.dto.ProdutoDTO;
import com.projeto.shopee.entities.Produto;
import com.projeto.shopee.entities.Status;
import com.projeto.shopee.entities.Loja;
import com.projeto.shopee.repository.ProdutoRepository;
import com.projeto.shopee.repository.StatusRepository; // Importando StatusRepository
import com.projeto.shopee.repository.LojaRepository; // Importando LojaRepository
import com.projeto.shopee.util.ProdutoMapper;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private StatusRepository statusRepository; // Injetando StatusRepository

    @Autowired
    private LojaRepository lojaRepository; // Injetando LojaRepository

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

    public ProdutoDTO createProduto(ProdutoDTO produtoDTO, Long statusId, Long lojaId) {
        Status status = statusRepository.findById(statusId).orElseThrow(() -> new RuntimeException("Status não encontrado"));
        Loja loja = lojaRepository.findById(lojaId).orElseThrow(() -> new RuntimeException("Loja não encontrada"));
        Produto produto = produtoMapper.toEntity(produtoDTO, status, loja);
        produto = produtoRepository.save(produto);
        return produtoMapper.toDTO(produto);
    }

    public ProdutoDTO updateProduto(Long id, ProdutoDTO produtoDTO) {
        if (!produtoRepository.existsById(id)) {
            return null;
        }
        produtoDTO.setId(id);
        Produto produto = produtoMapper.toEntity(produtoDTO, null, null); // Atualizando sem Status e Loja
        produto = produtoRepository.save(produto);
        return produtoMapper.toDTO(produto);
    }

    public void deleteProduto(Long id) {
        produtoRepository.deleteById(id);
    }
}
