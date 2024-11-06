package com.projeto.shopee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.shopee.dto.ProdutoDTO;
import com.projeto.shopee.entities.CategoriaProduto;
import com.projeto.shopee.entities.Loja;
import com.projeto.shopee.entities.Produto;
import com.projeto.shopee.entities.Status;
import com.projeto.shopee.repository.CategoriaProdutoRepository;
import com.projeto.shopee.repository.LojaRepository;
import com.projeto.shopee.repository.ProdutoRepository;
import com.projeto.shopee.repository.StatusRepository;
import com.projeto.shopee.util.ProdutoMapper;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @Autowired
    private StatusRepository statusRepository;

    public List<ProdutoDTO> getAllProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtoMapper.toDTOs(produtos);
    }

    public List<ProdutoDTO> getProdutosAtivos() {
        List<Produto> produtos = produtoRepository.findByStatusNomeStatus("Ativo");
        return produtoMapper.toDTOs(produtos);
    }

    public ProdutoDTO getProdutoById(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.map(produtoMapper::toDTO).orElse(null);
    }

    public ProdutoDTO createProduto(ProdutoDTO produtoDTO, Long usuarioId) {
        System.out.println("ProdutoDTO recebido: " + produtoDTO);

        Loja loja = lojaRepository.findByUsuarioId(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário não possui uma loja"));

        Produto produto = produtoMapper.toEntity(produtoDTO);
        produto.setLoja(loja);
        produto = produtoRepository.save(produto);
        return produtoMapper.toDTO(produto);
    }

    public ProdutoDTO updateProduto(Long id, ProdutoDTO produtoDTO) {
        System.out.println("ProdutoDTO para atualização: " + produtoDTO);

        Produto produtoExistente = produtoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produtoExistente.setNome(produtoDTO.getNome());
        produtoExistente.setDescricao(produtoDTO.getDescricao());
        produtoExistente.setPreco(produtoDTO.getPreco());
        produtoExistente.setEstoque(produtoDTO.getEstoque());
        produtoExistente.setImagem(produtoDTO.getImagem());

        CategoriaProduto categoria = categoriaProdutoRepository.findById(produtoDTO.getCategoriaProdutoId())
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        produtoExistente.setCategoriaProduto(categoria);

        Status status = statusRepository.findById(produtoDTO.getStatusId())
            .orElseThrow(() -> new RuntimeException("Status não encontrado"));
        produtoExistente.setStatus(status);

        if (produtoExistente.getLoja() == null) {
            throw new RuntimeException("Produto não está associado a uma loja");
        }

        produtoExistente = produtoRepository.save(produtoExistente);
        return produtoMapper.toDTO(produtoExistente);
    }

    public void deleteProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public List<ProdutoDTO> getProdutosByLojaId(Long lojaId) {
        List<Produto> produtos = produtoRepository.findByLojaId(lojaId);
        return produtoMapper.toDTOs(produtos);
    }

    public List<ProdutoDTO> getProdutosByLojaIdAndUsuario(Long lojaId, Long usuarioId) {
        @SuppressWarnings("unused")
        Loja loja = lojaRepository.findByIdAndUsuarioId(lojaId, usuarioId)
            .orElseThrow(() -> new RuntimeException("Loja não encontrada ou não pertence ao usuário"));

        List<Produto> produtos = produtoRepository.findByLojaId(lojaId);
        return produtoMapper.toDTOs(produtos);
    }
}