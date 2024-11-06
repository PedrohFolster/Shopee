package com.projeto.shopee.loja;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.projeto.shopee.util.ValidationUtils;

public class ValidaNomeLojaTest {

    @Test
    void testNomeLojaValido() {
        assertTrue(ValidationUtils.isValidNomeLoja("Loja A"), "Nome da loja deve ser válido");
    }

    @Test
    void testNomeLojaInvalido() {
        assertFalse(ValidationUtils.isValidNomeLoja(" "), "Nome da loja deve ser inválido");
        assertFalse(ValidationUtils.isValidNomeLoja("A"), "Nome da loja deve ser inválido");
        assertFalse(ValidationUtils.isValidNomeLoja(null), "Nome da loja deve ser inválido");
    }
} 