package com.projeto.shopee.usuario;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.projeto.shopee.util.ValidationUtils;

public class ValidaSenhaTest {

    @Test
    void testSenhaValida() {
        assertTrue(ValidationUtils.isValidPassword("Senha@123"), "Senha deve ser válida");
    }

    @Test
    void testSenhaInvalida() {
        assertFalse(ValidationUtils.isValidPassword("senha123"), "Senha deve ser inválida");
        assertFalse(ValidationUtils.isValidPassword("SENHA123"), "Senha deve ser inválida");
        assertFalse(ValidationUtils.isValidPassword("Senha"), "Senha deve ser inválida");
        assertFalse(ValidationUtils.isValidPassword("Senha123"), "Senha deve ser inválida");
    }
} 