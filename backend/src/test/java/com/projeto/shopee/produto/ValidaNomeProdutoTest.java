package com.projeto.shopee.produto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.projeto.shopee.util.ValidationUtils;

public class ValidaNomeProdutoTest {

    @Test
    void testNomeProdutoValido() {
        assertTrue(ValidationUtils.isValidNomeProduto("Produto A"), "Nome do produto deve ser válido");
    }

    @Test
    void testNomeProdutoInvalido() {
        assertFalse(ValidationUtils.isValidNomeProduto(" "), "Nome do produto deve ser inválido");
        assertFalse(ValidationUtils.isValidNomeProduto(null), "Nome do produto deve ser inválido");
        assertFalse(ValidationUtils.isValidNomeProduto("A".repeat(101)), "Nome do produto deve ser inválido");
    }
} 