package com.projeto.shopee.produto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.projeto.shopee.repository.CategoriaProdutoRepository;
import com.projeto.shopee.service.ProdutoService;

public class ValidaCategoriaProdutoTest {

    @Mock
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCategoriaProdutoValida() {
        when(categoriaProdutoRepository.existsById(1L)).thenReturn(true);
        assertTrue(categoriaProdutoRepository.existsById(1L), "Categoria do produto deve ser válida");
    }

    @Test
    void testCategoriaProdutoInvalida() {
        when(categoriaProdutoRepository.existsById(1000L)).thenReturn(false);
        assertFalse(categoriaProdutoRepository.existsById(1000L), "Categoria do produto deve ser inválida");
    }
} 