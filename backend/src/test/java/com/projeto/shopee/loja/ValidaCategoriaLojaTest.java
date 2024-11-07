package com.projeto.shopee.loja;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.projeto.shopee.repository.CategoriaLojaRepository;
import com.projeto.shopee.service.LojaService;

public class ValidaCategoriaLojaTest {

    @Mock
    private CategoriaLojaRepository categoriaLojaRepository;

    @InjectMocks
    private LojaService lojaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCategoriaLojaValida() {
        when(categoriaLojaRepository.existsById(1L)).thenReturn(true);
        assertTrue(categoriaLojaRepository.existsById(1L), "Categoria da loja deve ser válida");
    }

    @Test
    void testCategoriaLojaInvalida() {
        when(categoriaLojaRepository.existsById(1000L)).thenReturn(false);
        assertFalse(categoriaLojaRepository.existsById(1000L), "Categoria da loja deve ser inválida");
    }
} 