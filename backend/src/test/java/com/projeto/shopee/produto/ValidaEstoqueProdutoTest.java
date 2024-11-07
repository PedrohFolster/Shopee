package com.projeto.shopee.produto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.projeto.shopee.util.ValidationUtils;

public class ValidaEstoqueProdutoTest {

    @Test
    void testEstoqueProdutoValido() {
        assertTrue(ValidationUtils.isValidEstoque(100), "Estoque do produto deve ser válido");
    }

    @Test
    void testEstoqueProdutoInvalido() {
        assertFalse(ValidationUtils.isValidEstoque(-1), "Estoque do produto deve ser inválido");
        assertFalse(ValidationUtils.isValidEstoque(100000), "Estoque do produto deve ser inválido");
    }
} 