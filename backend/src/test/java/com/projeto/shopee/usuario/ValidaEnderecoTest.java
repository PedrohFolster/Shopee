package com.projeto.shopee.usuario;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.projeto.shopee.dto.EnderecoDTO;
import com.projeto.shopee.util.ValidationUtils;

import org.junit.jupiter.api.Test;

public class ValidaEnderecoTest {

    @Test
    void testEnderecoValido() {
        EnderecoDTO endereco = new EnderecoDTO(1L, "12345678", "Rua A", "123", "Cidade", "Estado", "Pais", "Complemento");
        assertTrue(ValidationUtils.isValidEndereco(endereco), "Endereço deve ser válido");
    }

    @Test
    void testEnderecoInvalido() {
        EnderecoDTO enderecoInvalido = new EnderecoDTO(1L, null, "Rua A", "123", "Cidade", "Estado", "Pais", "Complemento");
        assertFalse(ValidationUtils.isValidEndereco(enderecoInvalido), "Endereço deve ser inválido");
    }
} 