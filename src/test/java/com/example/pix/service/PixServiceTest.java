package com.example.pix.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.pix.model.PixPayment;
import com.example.pix.repository.PixRepository;

class PixServiceTest {

    @Mock
    private PixRepository repo;

    @InjectMocks
    private PixService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreatePixPayment() {
        PixPayment input = new PixPayment(null, "payer-1", "receiver-1", new BigDecimal("100.00"));
        PixPayment saved = new PixPayment("id-123", "payer-1", "receiver-1", new BigDecimal("100.00"));

        when(repo.save(any(PixPayment.class))).thenReturn(saved);

        PixPayment result = service.create(input);

        assertNotNull(result.getId());
        assertEquals("payer-1", result.getPayer());
        verify(repo, times(1)).save(any(PixPayment.class));
    }
    
}
