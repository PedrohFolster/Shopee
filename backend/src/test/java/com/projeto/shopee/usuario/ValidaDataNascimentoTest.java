package com.projeto.shopee.usuario;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.projeto.shopee.util.ValidationUtils;

public class ValidaDataNascimentoTest {

    @Test
    void testDataNascimentoValida() {
        assertTrue(ValidationUtils.isValidDataNascimento(LocalDate.of(2000, 1, 1)), "Data de nascimento deve ser válida");
    }

    @Test
    void testDataNascimentoInvalida() {
        assertFalse(ValidationUtils.isValidDataNascimento(LocalDate.of(2020, 1, 1)), "Data de nascimento deve ser inválida");
        assertFalse(ValidationUtils.isValidDataNascimento(null), "Data de nascimento deve ser inválida");
    }
} 