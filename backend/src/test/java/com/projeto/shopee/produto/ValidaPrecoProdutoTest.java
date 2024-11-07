package com.projeto.shopee.produto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.projeto.shopee.util.ValidationUtils;

public class ValidaPrecoProdutoTest {

    @Test
    void testPrecoProdutoValido() {
        assertTrue(ValidationUtils.isValidPreco(100.0), "Preço do produto deve ser válido");
    }

    @Test
    void testPrecoProdutoInvalido() {
        assertFalse(ValidationUtils.isValidPreco(-1.0), "Preço do produto deve ser inválido");
        assertFalse(ValidationUtils.isValidPreco(100000.0), "Preço do produto deve ser inválido");
    }
} 