package com.projeto.shopee.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.projeto.shopee.dto.LojaDTO;
import com.projeto.shopee.entities.Loja;
import com.projeto.shopee.exception.UsuarioJaPossuiLojaException;
import com.projeto.shopee.exception.InvalidLojaDataException;
import com.projeto.shopee.repository.LojaRepository;
import com.projeto.shopee.repository.CategoriaLojaRepository;
import com.projeto.shopee.util.LojaMapper;
import com.projeto.shopee.util.ValidationUtils;

@Service
public class LojaService {

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private CategoriaLojaRepository categoriaLojaRepository;

    @Autowired
    private LojaMapper lojaMapper;

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

    public LojaDTO createLoja(LojaDTO lojaDTO) throws UsuarioJaPossuiLojaException, InvalidLojaDataException {
        validateLojaData(lojaDTO);

        if (lojaRepository.existsByUsuarioId(lojaDTO.getUsuarioId())) {
            throw new UsuarioJaPossuiLojaException("Usuário já possui uma loja.");
        }

        if (!categoriaLojaRepository.existsById(lojaDTO.getCategoriaLojaId())) {
            throw new InvalidLojaDataException("Categoria da loja inválida.");
        }

        Loja loja = lojaMapper.toEntity(lojaDTO);
        Loja novaLoja = lojaRepository.save(loja);
        return lojaMapper.toDTO(novaLoja);
    }

    private void validateLojaData(LojaDTO lojaDTO) throws InvalidLojaDataException {
        if (!ValidationUtils.isValidNomeLoja(lojaDTO.getNome())) {
            throw new InvalidLojaDataException("O nome da loja é obrigatório e deve conter pelo menos 2 caracteres.");
        }
        if (!ValidationUtils.isValidCategoriaLoja(lojaDTO.getCategoriaLojaId())) {
            throw new InvalidLojaDataException("A categoria da loja é obrigatória.");
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
}