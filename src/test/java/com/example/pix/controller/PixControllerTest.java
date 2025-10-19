package com.example.pix.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.pix.dto.PixRequest;
import com.example.pix.dto.PixResponse;
import com.example.pix.exception.NotFoundException;
import com.example.pix.mapper.PixMapper;
import com.example.pix.model.PixPayment;
import com.example.pix.service.PixService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PixController.class)
class PixControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PixService service;

    @MockBean
    private PixMapper mapper;

    @Test
    void whenPostValidPix_thenShouldReturnCreated() throws Exception {
    	UUID newId = UUID.randomUUID();
        PixRequest request = new PixRequest("payer-123", "receiver-456", new BigDecimal("125.50"), "Teste de criação");
        PixPayment createdPayment = new PixPayment(newId, "payer-123", "receiver-456", new BigDecimal("125.50"), "Teste de criação", "PROCESSING", LocalDateTime.now());
        
        when(service.create(any(PixRequest.class))).thenReturn(createdPayment);

        mockMvc.perform(post("/api/pix")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "http://localhost/api/pix/" + newId));
    }
    
    @Test
    void whenPostInvalidPix_thenShouldReturnBadRequest() throws Exception {
        PixRequest invalidRequest = new PixRequest(null, "receiver-456", new BigDecimal("10.00"), "Inválido");
    
        mockMvc.perform(post("/api/pix")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void givenExistingId_whenGetById_thenShouldReturnPixResponse() throws Exception {
    	UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.of(2025, 9, 23, 17, 50, 0);
        PixPayment payment = new PixPayment(id, "payer-abc", "receiver-def", new BigDecimal("200.00"), "Consulta", "COMPLETED", now);
        PixResponse response = new PixResponse(id, "payer-abc", "receiver-def", new BigDecimal("200.00"), "Consulta", "COMPLETED", now);
        when(service.getById(id)).thenReturn(response);
        when(mapper.toResponse(payment)).thenReturn(response);
        mockMvc.perform(get("/api/pix/{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.payer").value("payer-abc"))
            .andExpect(jsonPath("$.receiver").value("receiver-def"))
            .andExpect(jsonPath("$.amount").value(200.00))
            .andExpect(jsonPath("$.status").value("COMPLETED"))
            .andExpect(jsonPath("$.createdAt").value("2025-09-23T17:50:00"));    
    }

    @Test
    void givenNonExistingId_whenGetById_thenShouldReturnNotFound() throws Exception {
    	UUID id = UUID.randomUUID();
        when(service.getById(id)).thenThrow(new NotFoundException("Pix not found with id: " + id));
        mockMvc.perform(get("/api/pix/{id}", id))
            .andExpect(status().isNotFound());
    }
}
