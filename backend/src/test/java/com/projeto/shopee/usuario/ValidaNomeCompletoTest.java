package com.projeto.shopee.usuario;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.projeto.shopee.util.ValidationUtils;

public class ValidaNomeCompletoTest {

    @Test
    void testNomeCompletoValido() {
        assertTrue(ValidationUtils.isValidNomeCompleto("João Silva"), "Nome completo deve ser válido");
    }

    @Test
    void testNomeCompletoInvalido() {
        assertFalse(ValidationUtils.isValidNomeCompleto("João"), "Nome completo deve ser inválido");
        assertFalse(ValidationUtils.isValidNomeCompleto(""), "Nome completo deve ser inválido");
        assertFalse(ValidationUtils.isValidNomeCompleto(null), "Nome completo deve ser inválido");
    }
} 