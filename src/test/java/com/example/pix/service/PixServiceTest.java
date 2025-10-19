package com.example.pix.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.pix.dto.PixRequest;
import com.example.pix.dto.PixResponse;
import com.example.pix.exception.ResourceNotFoundException;
import com.example.pix.mapper.PixMapper;
import com.example.pix.enuns.PaymentStatus;
import com.example.pix.model.PixPayment;
import com.example.pix.repository.PixRepository;

class PixServiceTest {

    @Mock
    private PixRepository repo;
    
    @Mock
    private PixMapper mapper;

    @InjectMocks
    private PixService service;

    private PixRequest validRequest;
    private PixPayment paymentEntity;
    private UUID id;

    @BeforeEach
    void setUp() {
    	id = UUID.randomUUID();
        MockitoAnnotations.openMocks(this);
        validRequest = new PixRequest("payer-1", "receiver-1", new BigDecimal("100.00"), "Pagamento de teste");
        paymentEntity = new PixPayment(id, "payer-1", "receiver-1", new BigDecimal("100.00"), "Pagamento de teste", "PROCESSING", LocalDateTime.now());
    }
    
    @Test
    void shouldCreatePixPaymentSuccessfully() {
        when(mapper.toEntity(any(PixRequest.class))).thenReturn(paymentEntity);
        
        when(repo.save(any(PixPayment.class))).thenAnswer(invocation -> {
            PixPayment paymentToSave = invocation.getArgument(0);
            if (paymentToSave.getId() == null) {
                paymentToSave.setId(UUID.randomUUID());
            }
            return paymentToSave;
        });

        PixPayment result = service.create(validRequest);

        assertNotNull(result.getId());
        assertNotNull(result.getCreatedAt());
        assertEquals(PaymentStatus.PROCESSING.toString(), result.getStatus());
        assertEquals("payer-1", result.getPayer());
        assertEquals("receiver-1", result.getReceiver());
        assertEquals(new BigDecimal("100.00"), result.getAmount());
        
        verify(mapper, times(1)).toEntity(validRequest);
        verify(repo, times(1)).save(any(PixPayment.class));
        
    }

    @Test
    void givenExistingId_whenGetById_thenShouldReturnPixPayment() {
    	 UUID existingId = UUID.randomUUID();
         PixPayment existing = new PixPayment(existingId, "payer-a", "receiver-b", new BigDecimal("100.00"), "Pagamento existente", "COMPLETED", LocalDateTime.now());
         PixResponse response = new PixResponse(existingId, "payer-a", "receiver-b", new BigDecimal("100.00"), "Pagamento existente", "COMPLETED", LocalDateTime.now());
         when(repo.findById(existingId)).thenReturn(Optional.of(existing));
         when(mapper.toResponse(any(PixPayment.class))).thenReturn(response);
         PixResponse result = service.getById(existingId);
        
        assertEquals(existingId, result.getId());
        assertEquals("payer-a", result.getPayer());
        assertEquals("receiver-b", result.getReceiver());
        assertEquals(0, new BigDecimal("100.00").compareTo(result.getAmount()));
        assertEquals(PaymentStatus.COMPLETED.toString(), result.getStatus());
        assertNotNull(result.getCreatedAt());
        
    }

    @Test
    void givenNonExistingId_whenGetById_shouldThrowNotFoundException() {
        UUID nonExistingId = UUID.randomUUID();
        when(repo.findById(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getById(nonExistingId));
    }

    @Test
    void givenNegativeAmount_whenCreate_thenShouldThrowIllegalArgumentException() {
        PixRequest requestWithNegativeAmount = new PixRequest("payer-x", "receiver-y", new BigDecimal("-1.00"), "Valor negativo");
        assertThrows(IllegalArgumentException.class, () -> service.create(requestWithNegativeAmount));
        verify(repo, never()).save(any());
    }
    
    @Test
    void givenZeroAmount_whenCreate_thenShouldThrowIllegalArgumentException() {
        PixRequest requestWithZeroAmount = new PixRequest("payer-z", "receiver-w", BigDecimal.ZERO, "Valor zero");
        assertThrows(IllegalArgumentException.class, () -> service.create(requestWithZeroAmount));
        verify(repo, never()).save(any());
    }
    
    @Test
    void givenNullAmount_whenCreate_thenShouldThrowIllegalArgumentException() {
        PixRequest requestWithNullAmount = new PixRequest("payer-n", "receiver-m", null, "Valor nulo");
        assertThrows(IllegalArgumentException.class, () -> service.create(requestWithNullAmount));
        verify(repo, never()).save(any());
    }
}
