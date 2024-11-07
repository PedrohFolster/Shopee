package com.projeto.shopee.produto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.projeto.shopee.util.ValidationUtils;

public class ValidaImagemProdutoTest {

    @Test
    void testImagemProdutoValida() {
        assertTrue(ValidationUtils.isValidImagem("imagem.jpg"), "Imagem do produto deve ser válida");
    }

    @Test
    void testImagemProdutoInvalida() {
        assertFalse(ValidationUtils.isValidImagem(" "), "Imagem do produto deve ser inválida");
        assertFalse(ValidationUtils.isValidImagem(null), "Imagem do produto deve ser inválida");
    }
} 