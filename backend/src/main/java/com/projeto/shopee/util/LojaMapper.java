package com.projeto.shopee.util;

import com.projeto.shopee.dto.LojaDTO;
import com.projeto.shopee.entities.Loja;
import com.projeto.shopee.entities.CategoriaLoja;
import com.projeto.shopee.entities.Usuario;
import com.projeto.shopee.repository.CategoriaLojaRepository;
import com.projeto.shopee.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LojaMapper {

    @Autowired
    private CategoriaLojaRepository categoriaLojaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public LojaDTO toDTO(Loja loja) {
        LojaDTO lojaDTO = new LojaDTO();
        lojaDTO.setId(loja.getId());
        lojaDTO.setNome(loja.getNome());

        // Mapeando o relacionamento CategoriaLoja (somente ID)
        if (loja.getCategoriaLoja() != null) {
            lojaDTO.setCategoriaLojaId(loja.getCategoriaLoja().getId());
        }

        // Mapeando o relacionamento Usuario (somente ID)
        if (loja.getUsuario() != null) {
            lojaDTO.setUsuarioId(loja.getUsuario().getId());
        }

        return lojaDTO;
    }

    public Loja toEntity(LojaDTO lojaDTO) {
        Loja loja = new Loja();
        loja.setId(lojaDTO.getId());
        loja.setNome(lojaDTO.getNome());

        // Mapeando o relacionamento CategoriaLoja
        if (lojaDTO.getCategoriaLojaId() != null) {
            CategoriaLoja categoriaLoja = categoriaLojaRepository.findById(lojaDTO.getCategoriaLojaId()).orElse(null);
            loja.setCategoriaLoja(categoriaLoja);
        }

        // Mapeando o relacionamento Usuario
        if (lojaDTO.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(lojaDTO.getUsuarioId()).orElse(null);
            loja.setUsuario(usuario);
        }

        return loja;
    }

    public List<LojaDTO> toDTOs(List<Loja> lojas) {
        return lojas.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<Loja> toEntities(List<LojaDTO> lojaDTOs) {
        return lojaDTOs.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
