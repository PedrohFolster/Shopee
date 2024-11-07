package com.projeto.shopee.produto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.projeto.shopee.util.ValidationUtils;

public class ValidaStatusProdutoTest {

    @Test
    void testStatusProdutoValido() {
        assertTrue(ValidationUtils.isValidStatus(1L), "Status do produto deve ser válido");
        assertTrue(ValidationUtils.isValidStatus(2L), "Status do produto deve ser válido");
    }

    @Test
    void testStatusProdutoInvalido() {
        assertFalse(ValidationUtils.isValidStatus(3L), "Status do produto deve ser inválido");
        assertFalse(ValidationUtils.isValidStatus(null), "Status do produto deve ser inválido");
    }
} 