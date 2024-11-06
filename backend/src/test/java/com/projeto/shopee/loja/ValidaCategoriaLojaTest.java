package com.projeto.shopee.loja;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.projeto.shopee.util.ValidationUtils;

public class ValidaCategoriaLojaTest {

    @Test
    void testCategoriaLojaValida() {
        assertTrue(ValidationUtils.isValidCategoriaLoja(1L), "Categoria da loja deve ser válida");
    }

    @Test
    void testCategoriaLojaInvalida() {
        assertFalse(ValidationUtils.isValidCategoriaLoja(null), "Categoria da loja deve ser inválida");
    }
} 