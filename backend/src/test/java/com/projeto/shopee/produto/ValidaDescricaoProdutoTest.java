package com.projeto.shopee.produto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.projeto.shopee.util.ValidationUtils;

public class ValidaDescricaoProdutoTest {

    @Test
    void testDescricaoProdutoValida() {
        assertTrue(ValidationUtils.isValidDescricao("Descrição válida"), "Descrição do produto deve ser válida");
    }

    @Test
    void testDescricaoProdutoInvalida() {
        assertFalse(ValidationUtils.isValidDescricao(" "), "Descrição do produto deve ser inválida");
        assertFalse(ValidationUtils.isValidDescricao(null), "Descrição do produto deve ser inválida");
        assertFalse(ValidationUtils.isValidDescricao("A".repeat(501)), "Descrição do produto deve ser inválida");
        assertFalse(ValidationUtils.isValidDescricao("1234"), "Descrição do produto deve ser inválida");
    }
} 