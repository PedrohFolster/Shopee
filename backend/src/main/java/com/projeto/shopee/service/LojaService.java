package com.projeto.shopee.service;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projeto.shopee.dto.LojaDTO;
import com.projeto.shopee.entities.Loja;
import com.projeto.shopee.exception.UsuarioJaPossuiLojaException;
import com.projeto.shopee.repository.LojaRepository;
import com.projeto.shopee.repository.ProdutoRepository;
import com.projeto.shopee.util.LojaMapper;
import com.projeto.shopee.util.ValidationUtils;
import com.projeto.shopee.entities.Produto;
import com.projeto.shopee.exception.InvalidLojaDataException;

@Service
public class LojaService {

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private LojaMapper lojaMapper;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<LojaDTO> getAllLojas() {
        List<Loja> lojas = lojaRepository.findAll();
        return lojaMapper.toDTOs(lojas);
    }

    public LojaDTO getLojaById(Long id) {
        Optional<Loja> loja = lojaRepository.findById(id);
        return loja.map(lojaMapper::toDTO).orElse(null);
    }

    public LojaDTO getLojaByUsuarioId(Long usuarioId) {
        Optional<Loja> lojaOptional = lojaRepository.findByUsuarioId(usuarioId);
        return lojaOptional.map(lojaMapper::toDTO).orElse(null);
    }

    public LojaDTO createLoja(LojaDTO lojaDTO) throws UsuarioJaPossuiLojaException {
        validateLojaData(lojaDTO);

        if (lojaRepository.existsByUsuarioId(lojaDTO.getUsuarioId())) {
            throw new UsuarioJaPossuiLojaException("Usuário já possui uma loja.");
        }

        Loja loja = lojaMapper.toEntity(lojaDTO);
        Loja novaLoja = lojaRepository.save(loja);
        return lojaMapper.toDTO(novaLoja);
    }

    private void validateLojaData(LojaDTO lojaDTO) {
        if (!ValidationUtils.isValidNomeLoja(lojaDTO.getNome())) {
            throw new InvalidLojaDataException("O nome da loja é obrigatório e deve conter pelo menos 2 caracteres.");
        }
    }

    public LojaDTO updateLoja(Long id, LojaDTO lojaDTO) {
        if (!lojaRepository.existsById(id)) {
            return null;
        }
        lojaDTO.setId(id);
        Loja loja = lojaMapper.toEntity(lojaDTO);
        loja = lojaRepository.save(loja);
        return lojaMapper.toDTO(loja);
    }

    public void deleteLoja(Long id) {
        lojaRepository.deleteById(id);
    }

    public boolean usuarioPossuiLoja(Long usuarioId) {
        return lojaRepository.existsByUsuarioId(usuarioId);
    }

    public List<LojaDTO> searchLojasByName(String nome) {
        List<Loja> lojas = lojaRepository.findByNomeContainingIgnoreCase(nome);
        return lojaMapper.toDTOs(lojas);
    }

    public Map<String, Object> getLojaInfoByUsuarioId(Long usuarioId) {
        Loja loja = lojaRepository.findByUsuarioId(usuarioId)
            .orElseThrow(() -> new RuntimeException("Loja não encontrada para o usuário"));

        List<Produto> produtos = produtoRepository.findByLojaId(loja.getId());
        int quantidadeProdutosDiferentes = produtos.size();
        int produtosTotais = produtos.stream().mapToInt(Produto::getEstoque).sum();

        Map<String, Object> lojaInfo = new HashMap<>();
        lojaInfo.put("nome", loja.getNome());
        lojaInfo.put("quantidadeProdutosDiferentes", quantidadeProdutosDiferentes);
        lojaInfo.put("produtosTotais", produtosTotais);
        lojaInfo.put("valorTotalEstoque", produtos.stream().mapToDouble(p -> p.getPreco() * p.getEstoque()).sum());

        return lojaInfo;
    }
}