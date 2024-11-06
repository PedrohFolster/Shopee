package com.projeto.shopee.usuario;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.projeto.shopee.util.ValidationUtils;

public class ValidaTelefoneTest {

    @Test
    void testTelefoneValido() {
        assertTrue(ValidationUtils.isValidTelefone("11987654321"), "Telefone deve ser válido");
    }

    @Test
    void testTelefoneInvalido() {
        assertFalse(ValidationUtils.isValidTelefone("987654321"), "Telefone deve ser inválido");
        assertFalse(ValidationUtils.isValidTelefone("1198765432a"), "Telefone deve ser inválido");
        assertFalse(ValidationUtils.isValidTelefone("119876543210"), "Telefone deve ser inválido");
    }
}