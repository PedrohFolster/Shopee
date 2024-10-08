package com.projeto.shopee.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ValidaCpfTest {

    @Test
    void testCpfValido() {
        assertTrue(ValidationUtils.isValidCpf("12345678909"), "CPF deve ser válido");
    }

    @Test
    void testCpfInvalido() {
        assertFalse(ValidationUtils.isValidCpf("12345678900"), "CPF deve ser inválido");
        assertFalse(ValidationUtils.isValidCpf("1234567890"), "CPF deve ser inválido");
        assertFalse(ValidationUtils.isValidCpf("123456789012"), "CPF deve ser inválido");
        assertFalse(ValidationUtils.isValidCpf("abcdefghijk"), "CPF deve ser inválido");
    }
}