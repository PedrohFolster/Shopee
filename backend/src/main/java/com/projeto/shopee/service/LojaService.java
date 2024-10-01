package com.projeto.shopee.service;

import com.projeto.shopee.dto.LojaDTO;
import com.projeto.shopee.entities.Loja;
import com.projeto.shopee.exception.UsuarioJaPossuiLojaException;
import com.projeto.shopee.repository.LojaRepository;
import com.projeto.shopee.util.LojaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LojaService {

    @Autowired
    private LojaRepository lojaRepository;

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

    public LojaDTO createLoja(LojaDTO lojaDTO) {
        // Verificar se o usuário já possui uma loja
        if (lojaRepository.existsByUsuarioId(lojaDTO.getUsuarioId())) {
            throw new UsuarioJaPossuiLojaException("Este usuário já possui uma loja cadastrada."); 
        }
        Loja loja = lojaMapper.toEntity(lojaDTO);
        loja = lojaRepository.save(loja);
        return lojaMapper.toDTO(loja);
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
}
